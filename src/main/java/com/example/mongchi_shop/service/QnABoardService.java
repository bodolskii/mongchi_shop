package com.example.mongchi_shop.service;

import com.example.mongchi_shop.dao.QnABoardDAO;
import com.example.mongchi_shop.domain.QnABoardVO;
import com.example.mongchi_shop.dto.QnABoardDTO;
import com.example.mongchi_shop.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum QnABoardService {
    INSTANCE;
    private QnABoardDAO qnABoardDAO;
    private ModelMapper modelMapper;

    QnABoardService() {
        qnABoardDAO=new QnABoardDAO();
        modelMapper= MapperUtil.INSTANCE.getInstance();
    }

    public QnABoardDTO getQnABoardByQno(int pno, int qno) throws Exception {
        log.info("pcode: " + pno);
        log.info("qno: " + qno);
        QnABoardVO qnABoardVO = qnABoardDAO.selectQnABoardByQno(pno, qno);
        log.info(qnABoardVO);
        QnABoardDTO qnABoardDTO = modelMapper.map(qnABoardVO, QnABoardDTO.class);
        log.info("qnABoardDTO: " + qnABoardDTO);
        return qnABoardDTO;
    }

    public int getAllQnAListCount(int pno) throws Exception {
        // 전체 qna수 가져오기
        int cnt=qnABoardDAO.getQnAListCount(pno);
        log.info("service cnt"+cnt);
        return cnt;
    }
    public int getMyQnAListCount(String emailId) throws Exception {
        int cnt = qnABoardDAO.getQnAListCountEmail(emailId);
        log.info(cnt+"COUNTCOUNT!!|");
        return cnt;
    }

    public List<QnABoardDTO> getQnABoardByPno(int pno, int currentPage, int limit) throws Exception {

        List<QnABoardVO> qnABoardVOList = qnABoardDAO.selectQnAByPno(pno, currentPage, limit);
        List<QnABoardDTO> qnABoardDTOList = new ArrayList<>();


        for(QnABoardVO vo:qnABoardVOList) {
            qnABoardDTOList.add(modelMapper.map(vo, QnABoardDTO.class));
        }
        return qnABoardDTOList;
    }

    public void addQnABoard(QnABoardDTO qnaBoardDTO) throws Exception {
        log.info("addQnaBoard...");
        log.info("qnaBoardDTO : " + qnaBoardDTO);
        QnABoardVO qnABoardVO = modelMapper.map(qnaBoardDTO, QnABoardVO.class);
        log.info("qnaBoardVO : " + qnABoardVO);
        qnABoardDAO.insertQnABoard(qnABoardVO);
    }

    public void modifyQuestionBoard(QnABoardDTO qnaBoardDTO) throws Exception {
        log.info("qnaBoardDTO: " + qnaBoardDTO);
        QnABoardVO qnABoardVO = modelMapper.map(qnaBoardDTO, QnABoardVO.class);
        log.info("qnaBoardVO: " + qnABoardVO);
        qnABoardDAO.updateQuestionBoard(qnABoardVO);
    }

    public void modifyAnswerBoard(QnABoardDTO qnaBoardDTO) throws Exception {
        QnABoardVO qnABoardVO=modelMapper.map(qnaBoardDTO, QnABoardVO.class);
        qnABoardDAO.updateAnswerBoard(qnABoardVO);
    }

    public void removeQnABoard(int qno) throws Exception {
        qnABoardDAO.deleteQnABoard(qno);
    }
    public List<QnABoardDTO> getQnABoardByEmailId(String emailId, int currentPage, int limit) throws Exception {
        // 마이페이지
        log.info("emailId: "+emailId);
        List<QnABoardVO> qnABoardVOList = qnABoardDAO.selectQnABoardByEmailId(emailId, currentPage, limit);
        List<QnABoardDTO> qnABoardDTOList = new ArrayList<>();
        for(QnABoardVO vo:qnABoardVOList) {
            qnABoardDTOList.add(modelMapper.map(vo, QnABoardDTO.class));
        }
        return qnABoardDTOList;
    }



}
