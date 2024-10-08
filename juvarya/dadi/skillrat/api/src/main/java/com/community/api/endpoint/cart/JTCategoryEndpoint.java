package com.community.api.endpoint.cart;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.community.api.endpoint.dto.JTCategoryDTO;
import com.community.api.endpoint.dto.JTCategoryListDTO;
import com.community.api.endpoint.dto.LoggedInUser;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.api.jwt.services.CustomerState;
import com.community.api.security.jwt.JwtUtils;
import com.community.core.azure.service.JTCustomerRoleService;
import com.community.core.catalog.domain.JTCategory;
import com.community.core.catalog.domain.JTCategoryToMedia;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.impl.JTCategoryImpl;
import com.community.core.catalog.service.JTCategoryService;
import com.community.core.catalog.service.JTCategoryToMediaService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/category", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTCategoryEndpoint extends JTBaseEndpoint {

	private static final String ADMIN = "ROLE_ADMIN";
	private static final String AUTHORIZATION = "Authorization";

	private static final int PAGE_NO = 0;
	private static final int PAGE_SIZE = 2000;

	@Autowired
	private ModelMapper modelMapper;

	@Resource(name = "blCatalogService")
	protected CatalogService catalogService;

	@Resource(name = "jtCategoryService")
	protected JTCategoryService jtCategoryService;

	@Resource(name = "jtCustomerRoleService")
	private JTCustomerRoleService customerRoleService;

	@Resource(name = "jtCategoryToMediaService")
	private JTCategoryToMediaService categoryToMediaService;

	@Resource(name = "customerState")
	private CustomerState customerState;
	
	@Autowired
	JwtUtils jwtUtils;

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/save")
	public ResponseEntity saveCategories(@RequestBody JTCategoryDTO jtCategoryDto, HttpServletRequest request) throws JsonProcessingException {
		String jwt = parseJwt(request);
		LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);

		if (loggedInUser.getRoles().contains(ADMIN)) {
			JTCategoryImpl jtcategory = new JTCategoryImpl();
			jtcategory.setName(jtCategoryDto.getName());
			jtcategory.setTopLevelCategory(jtCategoryDto.getTopLevelCategory());
			jtcategory.setCategoryOrder(jtCategoryDto.getCategoryOrder());
			jtcategory.setActive(jtCategoryDto.isActive());
			Category updateCategory = catalogService.saveCategory(jtcategory);
			JTCategoryDTO response = modelMapper.map(updateCategory, JTCategoryDTO.class);
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("You're not allowed to add category"));

	}

	@GetMapping("/list")
	public JTCategoryListDTO categorysList(HttpServletRequest request, @RequestParam int page,
			@RequestParam int pageSize) {
		JTCategoryListDTO categorylistdto = new JTCategoryListDTO();
		List<JTCategory> categories = jtCategoryService.CategorysList(page, pageSize);

		List<JTCategoryDTO> category = new ArrayList<>();
		for (JTCategory jtCategory : categories) {
			JTCategoryDTO categorydto = new JTCategoryDTO();
			categorydto = modelMapper.map(jtCategory, JTCategoryDTO.class);

			List<JTCategoryToMedia> images = categoryToMediaService.findByCategory(jtCategory.getId());
			List<String> media = new ArrayList<>();
			if (null != images) {
				for (JTCategoryToMedia categoryToMedia : images) {
					media.add(categoryToMedia.getMedia().getUrl());
				}
			}
			categorydto.setMedia(media);
			category.add(categorydto);
		}
		categorylistdto.setCategories(category);
		categorylistdto.setPage(page);
		categorylistdto.setPageSize(pageSize);
		return categorylistdto;
	}

	@GetMapping("/categories/list")
	public JTCategoryListDTO popularCategorys(@RequestParam int page, @RequestParam int pageSize,
			@RequestParam Boolean topLevelCategory) {
		List<JTCategory> jtcategories = jtCategoryService.findByPopular(topLevelCategory, page, pageSize);
		JTCategoryListDTO jtCategoryListDTO = new JTCategoryListDTO();
		List<JTCategoryDTO> jtCategoryDTOs = new ArrayList<>();
		for (JTCategory category : jtcategories) {
			JTCategoryDTO jtCategoryDTO = new JTCategoryDTO();
			jtCategoryDTO = modelMapper.map(category, JTCategoryDTO.class);
			List<JTCategoryToMedia> images = categoryToMediaService.findByCategory(jtCategoryDTO.getId());
			List<String> media = new ArrayList<>();
			if (null != images) {
				for (JTCategoryToMedia categoryToMedia : images) {
					media.add(categoryToMedia.getMedia().getUrl());
				}
			}
			jtCategoryDTO.setMedia(media);
			jtCategoryDTOs.add(jtCategoryDTO);
		}
		jtCategoryListDTO.setCategories(jtCategoryDTOs);
		jtCategoryListDTO.setPage(page);
		jtCategoryListDTO.setPageSize(pageSize);
		return jtCategoryListDTO;
	}

	@GetMapping("/details/{id}")
	public JTCategoryDTO findById(@PathVariable Long id, HttpServletRequest request) {
		JTCategory category = (JTCategory) catalogService.findCategoryById(id);
		return modelMapper.map(category, JTCategoryDTO.class);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/upload/media", consumes = { "multipart/form-data" })
	public ResponseEntity uploadMedia(@ModelAttribute JTCategoryDTO categoryDTO, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		String jwt = parseJwt(request);
		LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);

		if (loggedInUser.getRoles().contains(ADMIN)) {

			List<JTCategory> categories = jtCategoryService.CategorysList(PAGE_NO, PAGE_SIZE);
			Long categoryId = null;
			if (!categories.isEmpty()) {
				for (JTCategory category : categories) {
					if (category.getId().equals(categoryDTO.getId())) {
						categoryId = category.getId();
					}
				}
			}
			if (!categoryDTO.getFiles().isEmpty() && null != categoryId) {
				categoryToMediaService.upload(categoryDTO.getId(), categoryDTO.getFiles());
				return ResponseEntity.ok().body(new MessageResponse("image uploaded :-)"));
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Insufficient details"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("you're not allowed to add image"));
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/categories/byorder")
	public ResponseEntity getCategoriesByOrder(@RequestParam int page, @RequestParam int pageSize) {
		JTCategoryListDTO categorylistdto = new JTCategoryListDTO();
		List<JTCategory> categories = jtCategoryService.categorysListByOrder(page, pageSize);

		List<JTCategoryDTO> category = new ArrayList<>();
		for (JTCategory jtCategory : categories) {
			JTCategoryDTO categorydto = new JTCategoryDTO();
			categorydto = modelMapper.map(jtCategory, JTCategoryDTO.class);
			List<JTCategoryToMedia> images = categoryToMediaService.findByCategory(jtCategory.getId());
			List<String> media = new ArrayList<>();
			if (null != images) {
				for (JTCategoryToMedia categoryToMedia : images) {
					media.add(categoryToMedia.getMedia().getUrl());
				}
			}
			categorydto.setMedia(media);
			category.add(categorydto);
		}
		categorylistdto.setCategories(category);
		categorylistdto.setPage(page);
		categorylistdto.setPageSize(pageSize);
		return ResponseEntity.ok().body(categorylistdto);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(AUTHORIZATION);
		if (StringUtils.isNoneEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}
