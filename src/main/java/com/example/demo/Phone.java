package com.example.demo;

public class Phone {
	private long id;
	private String name;
	private String fio;
	private String age;
	private String serial;
	private String number;
	private String city;
	private String street;
	private String home;
	private String PhoneNum;

	public Phone(long id, String name,String fio,String age,String serial,String number,
				 String city,String street,String home,String PhoneNum) {
		super();
		this.id = id;
		this.name = name;
		this.fio = fio;
		this.age = age;
		this.serial = serial;
		this.number = number;
		this.city = city;
		this.street = street;
		this.home = home;
		this.PhoneNum = PhoneNum;
	}

	@Override
	public String toString() {
		return "Phone(id=" + id + ") [Имя =" + name + ",ФИО =" + fio + ",Возраст =" + age + ",Серия паспорта =" + serial +
				",Номер паспорта =" + number + ",Город =" + city + ",Улица =" + street + ",Дом =" + home +
				", Номер телефона=" + PhoneNum + "]</br>";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() { return name;}

	public void setName(String name) { this.name = name; }

	public String getFio() { return fio; }

	public void setFio(String fio) { this.fio = fio; }

	public String getAge() { return age; }

	public void setAge(String age) { this.age = age; }

	public String getSerial() { return serial; }

	public void setSerial(String serial) { this.serial = serial; }

	public String getNumber() { return number; }

	public void setNumber(String number) { this.number = number; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }

	public String getStreet() { return street; }

	public void setStreet(String street) { this.street = street; }

	public String getHome() { return home; }

	public void setHome(String home) { this.home = home; }

	public String getNum() { return PhoneNum; }

	public void setNum(String num) { this.PhoneNum = num; }


}
