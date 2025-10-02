package com.smitheartc.javakwic.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CIRCULAR_SHIFTS")
public class CircularShiftEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String circularShift;


    public CircularShiftEntity(String cs){
        this.circularShift = cs;
    }

    protected CircularShiftEntity() { }


    @Override
    public String toString() {
        return this.circularShift;
    }
}
