package bimr.df;

import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.inject.Inject;

import bimr.df.mapper.EbirdMapper;
import bimr.df.model.EbirdData;
import bimr.df.model.EbirdRequest;
import bimr.df.model.EbirdResponse;
import bimr.rf.ebird.EbirdServiceClient;
import bimr.rf.ebird.dao.EbirdDAO;

public class EbirdRepoBean implements EbirdRepo {

	@Inject
	private EbirdServiceClient ebirdsService;

	@Inject
	private EbirdDAO ebirdDAO;

	@Override
	public Future<EbirdResponse> retrieveEBirdData(EbirdRequest request) {
		return new AsyncResult<>(EbirdMapper.toEbirdsResponseFromWrapper(
				ebirdsService.retrieveEBirdData(EbirdMapper.fromEbirdRequestToWrapper(request))));
	}

	@Override
	public void insertEbirdData(List<EbirdData> ebirdDataList) {
		ebirdDAO.insertDataList(EbirdMapper.fromEbirdDataListToEntity(ebirdDataList));
	}

	@Override
	public Future<List<EbirdData>> retrieveEbirdDataFromDb() {
		// TODO Auto-generated method stub
		return null;
	}

}
