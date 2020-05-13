package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Phone;



@Controller
	public class MainController {
	    private static final Logger log = LoggerFactory.getLogger(MainController.class);

	    // Вводится (inject) из application.properties.
	    @Value("${welcome.message}")
	    private String message;
	    
	    
	    @Autowired
	    JdbcTemplate jdbcTemplate;

	    
	    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	    public String index(Model model) {
	    	List<Phone> lp = new ArrayList<>();
	    	jdbcTemplate.query(
	                "SELECT id, fio, num FROM phone",
	                (rs, rowNum) -> new Phone(rs.getLong("id"), rs.getString("name"), rs.getString("fio"),
							rs.getString("age"),rs.getString("serial"),rs.getString("number"),
							rs.getString("city"),rs.getString("street"),rs.getString("home"),rs.getString("PhoneNum"))
	        ).forEach(phone -> lp.add(phone));
	    	
	        model.addAttribute("message", message);
	        model.addAttribute("phones", lp);
	 
	        return "index";
	    }

	    @RequestMapping(value = { "/create" }, method = RequestMethod.GET)
	    public String createTable(){

	        log.info("Creating tables");

	        jdbcTemplate.execute("DROP TABLE phone IF EXISTS");
	        jdbcTemplate.execute("CREATE TABLE phone(" +
	                "id SERIAL, fio VARCHAR(255), num VARCHAR(255))");

	        
	        /*
	         * Arrays.asList - Создаем List из массива строк
	         * .stream() - Создаем из List поток (stream), можно сказать конвеер
	         * .map(inStr -> inStr.split(" ") - перобразуем каждую входную строку в массив строк, разделитель пробел
	         * .collect(Collectors.toList() - снова собираем все оъекты с конвеера в List и присваиваем переменной List<Object[]> splitUpNames
	         */
	        List<Object[]> splitUpNames = Arrays.asList("" +
					"Андрей Кахрин 24 1375 450001 Пермь Западная 20 79098531213",
					"Евгений Порийкин 27 1375 450007 Москва Северная 32 79058532468",
					"Дмитрий Глухих 20 1375 450062 Тюмень Восточная 1 79058531357",
					"Александ Самойленко 30 1375 450023 Красноярск Южная 89 79098537531",
					"Сергей Мещеряг 19 1375 450029 Самара Центральная 30 79058538642").stream()
	                .map(inStr -> inStr.split(" "))
	                .collect(Collectors.toList());

	        /*
	         * Распечатываем каждый объект из List splitUpNames
	         */
	        splitUpNames.forEach(inStr -> log.info(String.format("Добавлена запись: %s %s", inStr[0], inStr[1])));
	        
	        
	        /*
	         * Используем пакетное выполнение INSERT для каждого элемента LIst
	         */
	        jdbcTemplate.batchUpdate("INSERT INTO phone(name,fio,age,serial,number,city,street,home,PhoneNum) VALUES (?,?)", splitUpNames);
	        
	        /*
	         * Можно вставлять записи и так
	         */
	        jdbcTemplate.execute("INSERT INTO phone((name,fio,age,serial,number,city,street,home,PhoneNum)" +
					" VALUES ('Анатолий','Флока','35','1390','450101','Екатеринбург','Южно-Восточная','29','79091774546')");


	        log.info("Запрос записи, where fio = 'Мещеряг':");
			jdbcTemplate.query(
	                "SELECT id, fio, PhoneNum FROM phone WHERE fio = ?", new Object[] { "Флока" },
	                (rs, rowNum) -> new Phone(rs.getLong("id"), rs.getString("name"),rs.getString("fio"),
							rs.getString("age"),rs.getString("serial"),rs.getString("number"),rs.getString("city"),
							rs.getString("street"),rs.getString("home"),rs.getString("PhoneNub"))
	        ).forEach(phone -> log.info(phone.toString()));
	        
	        log.info("Запрос записи, where fio like 'Глухих%':");
			jdbcTemplate.query(
	                "SELECT id, name,fio, num FROM phone WHERE fio like ?", new Object[] { "Глухих%" },
	                (rs, rowNum) -> new Phone(rs.getLong("id"), rs.getString("name"), rs.getString("fio"),
							rs.getString("age"), rs.getString("serial"), rs.getString("number"), rs.getString("city")
							, rs.getString("street"), rs.getString("home"), rs.getString("PhoneNum"))
	        ).forEach(phone -> log.info(phone.toString()));
			
	        return "create";
	    }


}
