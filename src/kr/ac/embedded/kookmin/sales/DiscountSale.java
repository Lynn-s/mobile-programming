package kr.ac.embedded.kookmin.sales;

/**
 * Class for a sale of one item with discount expressed as a percent of the
 * price, but no other adjustments. Class invariant: The price is always
 * nonnegative; the name is a nonempty string; the discount is always
 * nonnegative.
 */

public class DiscountSale extends Sale {
	private double	discount;	// A percent of the price. Cannot be negative.
								
	public DiscountSale() {
		/** 구현 하시오 **/ 
		discount = 0;
	}
	
	/**
	 * Precondition: theName is a nonempty string; thePrice is nonnegative;
	 * theDiscount is expressed as a percent of the price and is nonnegative.
	 */
	public DiscountSale(String theName, double thePrice, double theDiscount) {
		/** 구현 하시오 **/ 
		setName(theName);
		setPrice(thePrice);
		setDiscount(theDiscount);
	}
	
	public DiscountSale(DiscountSale originalObject) {
		/** 구현 하시오 **/ 
		if (originalObject == null) {
			System.out.println("Error: null Sale object.");
			System.exit(0);
		}
		// else
		
	}
	
	public static void announcement() {
		System.out.println("This is the DiscountSale class.");
	}
	
	public double bill() {
		/** 구현 하시오 **/ 
		return getPrice()*((100-discount)*0.01);
	}
	
	public double getDiscount() {
		/** 구현 하시오 **/ 
		if(discount<0) {
			System.out.println("Error: Discount cannot be negative");
			System.exit(0);
		}
		
		return discount;
	}
	
	/**
	 * Precondition: Discount is nonnegative.
	 */
	public void setDiscount(double newDiscount) {
		/** 구현 하시오 **/ 
		if (newDiscount >= 0)
			discount = newDiscount;
		else {
			System.out.println("Error: Negative Discount.");
			System.exit(0);
		}
		
	}
	
	public String toString() {
		return (getName() + " Price = $" + getPrice() + " Discount = " + discount + "%\n" + "   Total cost = $" + bill());
	}
	
	public boolean equals(Object otherObject) {
		/** 구현 하시오 **/ 
		if (otherObject == null)
			return false;
		else if (getClass() != otherObject.getClass())
			return false;
		else {
			Sale otherSale = (Sale) otherObject;
			return (getName().equals(otherSale.getName()) && (getPrice() == otherSale.getPrice()));
		}
		
	}
	
	
	public DiscountSale clone() {
		/** 구현 하시오....  임시생성자 사용 **/ 
		return new DiscountSale(this);
	}
}
