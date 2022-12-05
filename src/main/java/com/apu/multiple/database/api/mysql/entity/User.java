package com.apu.multiple.database.api.mysql.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="USER_TABLE")
public class User {
	@Id
	private int id;
	private String firstName;
	private String lastName;
}
