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

	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya
	 * (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3:
	 * 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */

	public void setMonthlySalary(int grade) {
		if (grade == 1) {
			monthlySalary = 3000000;
			if (isForeigner) {
				monthlySalary = (int) (3000000 * 1.5);
			}
		} else if (grade == 2) {
			monthlySalary = 5000000;
			if (isForeigner) {
				monthlySalary = (int) (3000000 * 1.5);
			}
		} else if (grade == 3) {
			monthlySalary = 7000000;
			if (isForeigner) {
				monthlySalary = (int) (3000000 * 1.5);
			}
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
		/**
		 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus
		 * dibayarkan setahun.
		 * 
		 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan
		 * bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan)
		 * dikurangi penghasilan tidak kena pajak.
		 * 
		 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena
		 * pajaknya adalah Rp 54.000.000.
		 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah
		 * sebesar Rp 4.500.000.
		 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya
		 * ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
		 * 
		 */
		int tax = 0;

		if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}

		if (numberOfChildren > 3) {
			numberOfChildren = 3;
		}

		if (isMarried) {
			tax = (int) Math.round(0.05 * (((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible
					- (54000000 + 4500000 + (numberOfChildren * 1500000))));
		} else {
			tax = (int) Math.round(
					0.05 * (((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible - 54000000));
		}

		if (tax < 0) {
			return 0;
		} else {
			return tax;
		}

	}
}
