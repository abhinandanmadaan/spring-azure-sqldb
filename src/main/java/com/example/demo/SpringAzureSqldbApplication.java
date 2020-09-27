package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringAzureSqldbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAzureSqldbApplication.class, args);
	}
}

//@Component
//@RequiredArgsConstructor
//class SqlServerDemo{
//	private final JdbcTemplate template;
//	@EventListener(ApplicationReadyEvent.class)
//	public void ready() {
//		List<Demo> codes = this.template.query("select top 5 CustomerID from [SalesLT].[Customer]", new RowMapper<Demo>() {
//			@Override
//			public Demo mapRow(ResultSet resultSet, int i) throws SQLException{
//				return new Demo (resultSet.getInt("CustomerID"));
//			}
//		});
//		for(int i = 0; i<codes.size();i++)
//			System.out.println(codes.get(i));
//	}
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Demo{
//	private Integer code;
//}