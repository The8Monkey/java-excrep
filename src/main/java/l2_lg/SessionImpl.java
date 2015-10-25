package l2_lg;

import java.util.Date;
import java.util.List;

import l2_lg.Client.EmptyPhoneNumberExc;
import l2_lg.Client.IncredibleBirthDateExc;
import l2_lg.Client.PhoneNumberFormatExc;
import multex.Exc;

import l3_da.Persistence;

public class SessionImpl implements Session {

	private final Persistence persistence = new Persistence();
	
	/*package*/ Persistence getPersistence(){return this.persistence;}
	
	/* (non-Javadoc)
	 * @see l2_lg.Session#createClient(java.lang.String, java.lang.String, java.util.Date, java.lang.String)
	 */
	public Client createClient(final String firstName, final String lastName, final Date birthDate, final String phone) throws PhoneNumberFormatExc, IncredibleBirthDateExc, EmptyPhoneNumberExc{
		final Client result = new Client(this.persistence.getNextId(), firstName, lastName, birthDate, phone);
		this.persistence.save(result);
		return result;
	}

	/* (non-Javadoc)
	 * @see l2_lg.Session#delete(l2_lg.Client)
	 */
	public void delete(final Client client){
		this.persistence.delete(client);		
	}
	
	/* (non-Javadoc)
	 * @see l2_lg.Session#findClient(long)
	 */
	public Client findClient(final long id) throws Exc{
		return this.persistence.load(Client.class, id);
	}
	
	/* (non-Javadoc)
	 * @see l2_lg.Session#searchAllClients()
	 */
	public List<Client> searchAllClients() throws Exc{
		return this.persistence.search(Client.class);
	}
	
	/* (non-Javadoc)
	 * @see l2_lg.Session#commit()
	 */
	public void commit(){
		this.persistence.commit();
	}

}
