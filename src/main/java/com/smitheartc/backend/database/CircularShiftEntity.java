package com.smitheartc.backend.database;

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

    @Column int urlHits;

    @Column
    private int hash;

    @Column
    private boolean shifted;


    public CircularShiftEntity(String cs, String u, int h, boolean s) {
        this.circularShift = cs;
        this.url = u;
        this.hash = h;
        this.shifted = s;
    }

    protected CircularShiftEntity() { }


    @Override
    public String toString() {
        return this.circularShift;
    }
}
