package bimr.rf.ebird;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.Test;

import bimr.rf.ebird.wrapper.EbirdRequestWrapper;
import bimr.rf.ebird.wrapper.EbirdResponseWrapper;

public class EbirdsServiceClientBeanTest {

	@Test
	public void testRetrieveEbirdData() {

		EbirdRequestWrapper request = new EbirdRequestWrapper();
		
		request.setRequestUriPattern(
				"mockedUri");

		EbirdsServiceClientBean client = mock(EbirdsServiceClientBean.class);

		EbirdResponseWrapper response = mock(EbirdResponseWrapper.class);
		
		when(client.retrieveEBirdData(request)).thenReturn(response);

		assertNotNull(response);
		assertNotNull(response.getEbirdData());
	}
}