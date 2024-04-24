package lib;

import java.util.LinkedList;
import java.util.List;
import java.util.Date;

public class Employee {

	private String idNumber;

	private Date JoinedDate;
	private int monthWorkingInYear;

	private boolean isForeigner;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(String idNumber, Date JoinedDate, boolean isForeigner) {
		this.idNumber = idNumber;
		this.JoinedDate = JoinedDate;
		this.isForeigner = isForeigner;

		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}

	public void setMonthlySalary(int grade) {
		monthlySalary = 3000000 + (2000000 * grade);
		if (isForeigner) {
			monthlySalary *= 1.5;
		}
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	public void setSpouse(String spouseIdNumber) {
		this.spouseIdNumber = idNumber;
	}

	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}

	public int getAnnualIncomeTax() {

		// Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah
		// bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		Date date = new Date();

		if (date.getYear() == JoinedDate.getYear()) {
			monthWorkingInYear = date.getMonth() - JoinedDate.getMonth();
		} else {
			monthWorkingInYear = 12;
		}

		return calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible,
				spouseIdNumber.equals(""), childIdNumbers.size());
	}

	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible,
			boolean isMarried, int numberOfChildren) {
		int tax = 0;

		if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}

		int penghasilanBebasPajak = 54000000;
		if (isMarried) {
			penghasilanBebasPajak += 4500000 + (Math.min(numberOfChildren, 3) * 4500000);
		}

		tax = (int) Math.round(0.05
				* (((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible - penghasilanBebasPajak));

		if (tax < 0) {
			return 0;
		} else {
			return tax;
		}

	}
}
