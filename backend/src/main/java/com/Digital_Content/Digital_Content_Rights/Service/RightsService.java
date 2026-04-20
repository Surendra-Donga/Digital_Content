package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.Entity.ContentRights;
import com.Digital_Content.Digital_Content_Rights.Entity.RightsTransferHistory;
import com.Digital_Content.Digital_Content_Rights.Repository.ContentRightsRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.RightsTransferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RightsService {

    @Autowired
    private ContentRightsRepository rightsRepository;

    @Autowired
    private RightsTransferHistoryRepository transferHistoryRepository;

    public List<ContentRights> getRightsByContent(Integer contentId) {
        return rightsRepository.findByDigitalContent_Id(contentId);
    }

    @Transactional
    public ContentRights assignRights(ContentRights rights) {
        return rightsRepository.save(rights);
    }

    @Transactional
    public RightsTransferHistory transferRights(RightsTransferHistory history) {
        return transferHistoryRepository.save(history);
    }
}
