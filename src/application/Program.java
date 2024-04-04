package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.next();
		System.out.println();
		List<Sale> list = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				
				Sale sale = new Sale(month, year, seller, items, total);
				list.add(sale);
				
				line = br.readLine();
			}
			
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			
			
			List<Sale> newList = list.stream()
					.filter(x -> x.getYear() == 2016)
					.sorted((s1,s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
					.limit(5).collect(Collectors.toList());
			
			newList.forEach(System.out::println);
			System.out.println();
			
			double sum = list.stream().filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal())
					.reduce((double )0.0 ,(x,y) -> x+y);
			
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum));
	
		}
		catch(IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		
		sc.close();
	}

}
