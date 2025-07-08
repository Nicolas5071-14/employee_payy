package com.cbozan.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.cbozan.exception.EntityException;
import com.cbozan.util.DBConst;

import java.util.Objects;

public final class Employer implements Serializable, Cloneable {

    private static final long serialVersionUID = -465227783930564456L;

    private int employer_id;
    private String name;
    private String surname;
    private String phonenumber;
    private String business;
    private Timestamp date;

    private Employer() {
        this.employer_id = 0;
        this.name = null;
        this.surname = null;
        this.phonenumber = null;
        this.business = null;
        this.date = null;
    }

    private Employer(Employer.EmployerBuilder builder) throws EntityException {
        super();
        setEmployer_id(builder.employer_id);
        setName(builder.name);
        setSurname(builder.surname);
        setPhonenumber(builder.phonenumber);
        setBusiness(builder.business);
        setDate(builder.date);
    }

    // Builder class
    public static class EmployerBuilder {

        private int employer_id;
        private String name;
        private String surname;
        private String phonenumber;
        private String business;
        private Timestamp date;

        public EmployerBuilder() {
        }

        public EmployerBuilder(int employer_id, String name, String surname, String phonenumber, String business, Timestamp date) {
            super();
            this.employer_id = employer_id;
            this.name = name;
            this.surname = surname;
            this.phonenumber = phonenumber;
            this.business = business;
            this.date = date;
        }

        public EmployerBuilder setEmployer_id(int employer_id) {
            this.employer_id = employer_id;
            return this;
        }

        public EmployerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EmployerBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public EmployerBuilder setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
            return this;
        }

        public EmployerBuilder setBusiness(String business) {
            this.business = business;
            return this;
        }

        public EmployerBuilder setDate(Timestamp date) {
            this.date = date;
            return this;
        }

        public Employer build() throws EntityException {
            return new Employer(this);
        }

    }

    private static class EmptyInstanceSingleton {

        private static final Employer instance = new Employer();
    }

    public static final Employer getEmptyInstance() {
        return EmptyInstanceSingleton.instance;
    }

    public int getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(int employer_id) throws EntityException {
        if (employer_id <= 0) {
            throw new EntityException("Employer ID negative or zero");
        }
        this.employer_id
                = employer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws EntityException {
        if (name.length() == 0 || name.length() > DBConst.NAME_LENGTH) {
            throw new EntityException("Employer name empty or too long");
        }
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws EntityException {
        if (surname.length() == 0 || surname.length() > DBConst.SURNAME_LENGTH) {
            throw new EntityException("Employer last name empty or too long");
        }
        this.surname = surname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, business, name, employer_id, surname, phonenumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Employer other = (Employer) obj;
        return Objects.equals(date, other.date) && Objects.equals(business, other.business)
                && Objects.equals(name, other.name) && employer_id == other.employer_id && Objects.equals(surname, other.surname)
                && Objects.equals(phonenumber, other.phonenumber);
    }

    @Override
    public Employer clone() {
        // TODO Auto-generated method stub
        try {
            return (Employer) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
