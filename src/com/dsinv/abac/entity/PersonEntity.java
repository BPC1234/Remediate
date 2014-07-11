package com.dsinv.abac.entity;

import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;

import javax.persistence.*;

@Entity
@Table(name="person_information")
public class  PersonEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
		
	@Column(name = "name")
    private String name;

	@Column(name = "tax_ein_no")
    private String taxEinNo;
	
	@Column(name = "bank_account_no")
    private String bankAccountNo;
	
	@Column(name = "bank_account_location")
    private String bankAccountLocation;
	
	@Column(name = "type")
    private String type;

	@Column(name = "address")
	private String address;

    @Column(name = "address1")
	private String address1;
    
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
    private String state;
	
	@Column(name = "zip_code")
    private String zipCode;

	@Column(name = "country_name")
    private String country;
	
	@Column(name = "discriminator_value")
    private String discriminator;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "gender")
    private String gender;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTaxEinNo() {
		return taxEinNo;
	}

	public void setTaxEinNo(String taxEinNo) {
		this.taxEinNo = taxEinNo;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountLocation() {
		return bankAccountLocation;
	}

	public void setBankAccountLocation(String bankAccountLocation) {
		this.bankAccountLocation = bankAccountLocation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

    public void setId(long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taxEinNo='" + taxEinNo + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", bankAccountLocation='" + bankAccountLocation + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", discriminator='" + discriminator + '\'' +
                '}';
    }
}
