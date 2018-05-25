package com.bb.bloomrentalejb.external;

import java.util.Random;
import com.bb.bloomrentaldomain.RentalApp;

public class CreditScoreService {
	private static Random random = new Random();

	public void getCreditScore(Object obj) {
		RentalApp app = (RentalApp) obj;
		Integer num = random.nextInt(300) + 500;
		app.setCreditScore(num);
		System.out.println("CreditScoreService called");
	}
}
