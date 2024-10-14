package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatDTO {
    @XmlElement
    @Positive
    private float shooting;

    @XmlElement
    @Positive
    private float passing;

    @XmlElement
    @Positive
    private float endurance;

    public float getShooting() {
        return shooting;
    }

    public float getPassing() {
        return passing;
    }

    public float getEndurance() {
        return endurance;
    }
}
