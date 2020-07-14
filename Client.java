import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
public class Client {

    public Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry r = LocateRegistry.getRegistry(host);
            Hello stub = (Hello) r.lookup("Hello");
               String name,id,response;
		int op=1;
            while(op!=0)
			{
            Scanner in = new Scanner(System.in);
            System.out.println(" Enter your choice :");
            System.out.println("1.Vote Registration");
            System.out.println("2.Verification of the Voter");
            System.out.println("3.Want to Vote");
            System.out.println("4.Tally the results");
            System.out.println("5.Announce the Winner");
            System.out.println("0.Quit");
            System.out.println(" ");
            op = in.nextInt();
            in.nextLine();
            switch(op)
			{
                case 1 : System.out.println("Enter voter name:");
				name=in.nextLine();
				response=stub.registerVoter(name);
				System.out.println(response);
                    
				case 2 :    System.out.println("Enter Voter ID");
                id=in.nextLine();
                response=stub.verifyVoter(id);
                System.out.println(response);
                        
				case 3 :  System.out.println("Enter Candidate Name and Your Voter ID");
                name=in.nextLine();
                id=in.nextLine();
                response=stub.vote(name,id);
                System.out.println(response);
                          
            	case 4 :   Map<String,Integer> result=stub.tallyResults();
                for(Map.Entry<String,Integer> et:result.entrySet())
                System.out.println("Candidate Name:"+et.getKey()+" votes:"+et.getValue());
             
            	case 5 :    String st=stub.winner();
                System.out.println(st);
            
				case 0 :    break;
            
				default :    System.out.println("Invalid Option");
            
        	} //end of switch case
		} //end of while loop
	} //end of try
		catch (Exception e) 
		{
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
		} //end of catch
		
    } //main method ends
}//end of class