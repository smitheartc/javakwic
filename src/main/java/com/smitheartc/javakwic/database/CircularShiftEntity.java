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

    @Column
    private String url;


    public CircularShiftEntity(String cs, String u){
        this.circularShift = cs;
        this.url = u;
    }

    protected CircularShiftEntity() { }


    @Override
    public String toString() {
        return this.circularShift;
    }
}
