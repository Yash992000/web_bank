import java.util.*;
import java.io.*;

class Accountant implements Serializable
{
	private String accountantName;
	private String accountNumber;
	private String accountPassword;
	private int bankBalance;
	private String email;
	private String phone;
	public Accountant()
	{
		accountantName = "default";
		accountPassword = "default";
		accountNumber = "default";
		bankBalance = 0;
		email = "default";
		phone = "default";
	}
	public Accountant(String accountantName,String accountNumber, String accountPassword, int bankBalance, String email, String phone)
	{
		this.accountantName = accountantName;
		this.accountNumber = accountNumber;
		this.accountPassword = accountPassword;
		this.bankBalance = bankBalance;
		this.email = email;
		this.phone = phone;
	}
	public void setAccountantName(String accountantName)
	{
		this.accountantName = accountantName;
	}
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	public void setAccountPassword(String accountPassword)
	{
		this.accountPassword = accountPassword;
	}
	public void setBankBalance(int bankBalance)
	{
		this.bankBalance = bankBalance;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getAccountantName()
	{
		return this.accountantName;
	}
	public String getAccountNumber()
	{
		return this.accountNumber;
	}
	public String getAccountPassword()
	{
		return this.accountPassword;
	}
	public int getBankBalance()
	{
		return this.bankBalance;
	}
	public String getEmail()
	{
		return this.email;
	}
	public String getPhone()
	{
		return this.phone;
	}
}
class BankAccount extends Accountant
{

	public void createBankAccount() throws IOException{
		Scanner sc = new Scanner(System.in);
		Accountant acn = new Accountant();
		Random random = new Random();

			FileOutputStream fos = new FileOutputStream("account.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

				System.out.println();
				
        		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * *");
        		System.out.println("* -----------------------------------------------");
        		System.out.println("*              Enter Your Details                ");
        		System.out.println("* -----------------------------------------------");
				System.out.print("* Enter Your Name : ");
				String name = sc.nextLine();
				acn.setAccountantName(name);

				System.out.print("* Enter Your Email : ");
				String email = sc.nextLine();
				acn.setEmail(email);
				
				System.out.print("* Enter Your Phone Number : ");
				String phone = sc.nextLine();
				acn.setPhone(phone);
				
				System.out.print("* Enter Your Password : ");
				String password = sc.nextLine();
				acn.setAccountPassword(password);
        		System.out.println("*");

				long num = 1000000000L + random.nextLong(9000000000L);
        		String accNum = String.valueOf(num);
        		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * *");
        		System.out.println("* --------------------------------------------- *");
        		System.out.println("* | Your Account Number is : " + accNum + "       | *");
        		System.out.println("* --------------------------------------------- *");
        		System.out.println("*          Note Down Your Password              *");
        		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * *");
        		acn.setAccountNumber(accNum);

				oos.writeObject(acn);
				oos.flush();
				oos.close();
				System.out.println("Account Created Successfully...");
				System.out.println("- - - Login To Your Account To Make Transaction - - -");

	}

	public void userLogin() throws IOException, ClassNotFoundException{
		Scanner sc = new Scanner(System.in);
		
			System.out.println("* * * * * * * * * * * * * * * * * * * * *");
			System.out.print("* Enter Your Email : ");
			String accEmail = sc.nextLine();
			System.out.print("* Enter Your Password : ");
			String accPass = sc.nextLine();
			System.out.println("* * * * * * * * * * * * * * * * * * * * *");

			FileInputStream fis = new FileInputStream("account.txt");
	        ObjectInputStream ois = new ObjectInputStream(fis);

	        boolean loginSuccessful = false;

            // Check if the file is empty
	        if (fis.available() > 0) 
	        {
	            Accountant acn = (Accountant) ois.readObject();
	            if (acn.getEmail().equals(accEmail) && acn.getAccountPassword().equals(accPass)) 
	            {
	                loginSuccessful = true;
	            }
	        }
            if(loginSuccessful) 
            {
                System.out.println("* Login successful!");
				System.out.println("* * * * * * * * * * * * * * * * * * * * *");
            } 
            else 
            {
                System.out.println("* Invalid email or password.");
				System.out.println("* * * * * * * * * * * * * * * * * * * * *");
            }
	}

	private Accountant readAccountantFromFile() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("account.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            if (fis.available() > 0) {
                return (Accountant) ois.readObject();
            }
        }
        return null;
    }

    private void writeAccountantToFile(Accountant acn) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("account.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(acn);
            oos.flush();
        }
    }

	public void depositMoney() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        Accountant acn = readAccountantFromFile();
        if (acn == null) return;

        System.out.print("Enter the amount to deposit: ");
        int amount = sc.nextInt();
        acn.setBankBalance(acn.getBankBalance() + amount);

        writeAccountantToFile(acn);

        System.out.println("Amount deposited successfully.");
        System.out.println("-------------------------------------------");
        System.out.println("| New balance: " + acn.getBankBalance() + "                      |");
        System.out.println("-------------------------------------------");    
    }
	
	public void withdrawMoney() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        Accountant acn = readAccountantFromFile();
        if (acn == null) return;

        System.out.print("Enter the amount to withdraw: ");
        int amount = sc.nextInt();

        if (amount > acn.getBankBalance()) {
            System.out.println("Insufficient funds.");
        } else {
            acn.setBankBalance(acn.getBankBalance() - amount);
            writeAccountantToFile(acn);

            System.out.println("Amount withdrawn successfully.");
            System.out.println("-------------------------------------------");
            System.out.println("| New balance: " + acn.getBankBalance() + "                      |");
            System.out.println("-------------------------------------------"); 
        }
    }

    public void bankBalance() throws IOException, ClassNotFoundException {
        Accountant acn = readAccountantFromFile();
        if (acn != null) {
            System.out.println("-------------------------------------------");
            System.out.println("| Your current balance is: " + acn.getBankBalance()+ "            |");
            System.out.println("-------------------------------------------");

        }
    }

    public void bankDetails() throws IOException, ClassNotFoundException {
        Accountant acn = readAccountantFromFile();
        if (acn != null) {
        	System.out.println(" _____________________________________________");
        	System.out.println("|     * A C C O U N T    D E T A I L S *      |");
        	System.out.println("+---------------------------------------------+ ");
            System.out.println("| Name: " + acn.getAccountantName());
            System.out.println("| Account Number: " + acn.getAccountNumber());
            System.out.println("| Email: " + acn.getEmail());
            System.out.println("| Phone: " + acn.getPhone());
            System.out.println("| Bank Balance: " + acn.getBankBalance());
        	System.out.println("|_____________________________________________");

        }
    }

}

class ATM
{
	public static void main(String args[]){
		try{
		Scanner sc = new Scanner(System.in);
		BankAccount bnkacnt = new BankAccount();
		boolean loggedIn = false;
		while(true){
			System.out.println();
			System.out.print("Do You Have Account (Y/N) : ");
			char ans = sc.next().charAt(0);
			
			
			if(ans == 'y' || ans == 'Y')
			{
				bnkacnt.userLogin();
				loggedIn = true;
				while (loggedIn) {
					System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
					System.out.println("* --------------------------------------- *");
					System.out.println("*            Transaction Menu             *");
					System.out.println("* --------------------------------------- *");
					System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
					System.out.println("* 1. Deposit Money                        *");
					System.out.println("* 2. Withdraw Money                       *");
					System.out.println("* 3. Bank Balance                         *");
					System.out.println("* 4. Bank Details                         *");
					System.out.println("* 5. Logout                               *");
					System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
					System.out.print("* Enter Your Choice (1-5) : ");
					int ch = sc.nextInt();
					System.out.println("* * * * * * * * * * * * * * * * * * * * * *");

					switch(ch)
					{
						case 1:
							bnkacnt.depositMoney();
							break;
						case 2:
							bnkacnt.withdrawMoney();
							break; 
						case 3:
							bnkacnt.bankBalance();
							break;
						case 4:
							bnkacnt.bankDetails();
							break;
						case 5:
							System.out.println("You are Logged Out . . . ");
							loggedIn = false;
							break;
						default:
							System.out.println("Invalid Input...Please Enter Correct Choice");
					}

				}
				break;	
			}
			else if(ans == 'n' || ans == 'N')
			{
				System.out.print("Want to Open Account (Y/N) : ");
				char ans1 = sc.next().charAt(0);
			
				if(ans1 == 'y' || ans1 == 'Y')
				{
					bnkacnt.createBankAccount();
					break;
				}
				else if(ans1 == 'n' || ans1 == 'N')
				{
					System.out.println("Thank You !!! ... Visit Again !!! ... ");
					break;
				} 
				else{
					System.out.println("Wrong Input !!!");
					break;
			}
			}
			else{
				System.out.println("Wrong Input !!!");
				break;
			}			

		}
		} 
		catch (IOException e) {
        	System.out.println("I/O error: " + e.getMessage());
    	}
    	catch (ClassNotFoundException e) {
        	System.out.println("ClassNotFoundException error: " + e.getMessage());
    	}
	}
}