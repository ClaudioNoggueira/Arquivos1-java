package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		List<Product> products = new ArrayList<>();

		try {
			System.out.println("Entre com o caminho do diretório (pasta):");
			String strPath = input.nextLine();

			File sourceFile = new File(strPath);
			String strSourceFolder = sourceFile.getParent();

			boolean success = new File(strSourceFolder + "\\out").mkdir();

			String strTargetFile = strSourceFolder + "\\out\\summary.csv";

			try (BufferedReader br = new BufferedReader(new FileReader(strPath))) {
				String line = br.readLine();
				while (line != null) {
					String[] values = line.split(",");
					products.add(new Product(values[0], Double.parseDouble(values[1]), Integer.parseInt(values[2])));
					line = br.readLine();
				}

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(strTargetFile))) {
					for (Product prod : products) {
						bw.write(prod.getName() + ", " + String.format("%.2f", prod.total()));
						bw.newLine();
					}
					System.out.println(strTargetFile + " CRIADO!");
				} catch (IOException e) {
					System.out.println("Erro escrevendo arquivo: " + e.getMessage());
				}
			} catch (IOException e) {
				System.out.println("Erro lendo arquivo: " + e.getMessage());
			}
		} catch (InputMismatchException e) {
			System.out.println("Erro de entrada: " + e.getMessage());
		} finally {
			if (input != null)
				input.close();
		}
	}
}
