package com.juvarya.kovela.customer.model;

import lombok.Data;

import javax.persistence.*;

import com.juvarya.kovela.model.ERole;

@Entity
@Table(name = "roles",
indexes = {@Index(name = "idx_roleid",  columnList="id", unique = true)})
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}