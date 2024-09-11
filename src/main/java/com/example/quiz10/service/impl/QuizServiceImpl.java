package com.example.quiz10.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz10.constants.ResMessage;
import com.example.quiz10.constants.SelectType;
import com.example.quiz10.entity.Feedback;
import com.example.quiz10.entity.Ques;
import com.example.quiz10.entity.Quiz;
import com.example.quiz10.repository.FeedbackDao;
import com.example.quiz10.repository.QuesDao;
import com.example.quiz10.repository.QuizDao;
import com.example.quiz10.service.ifs.QuizService;
import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.CreateUpdateReq;
import com.example.quiz10.vo.DeleteReq;
import com.example.quiz10.vo.FillinReq;
import com.example.quiz10.vo.QuizRes;
import com.example.quiz10.vo.SearchReq;
import com.example.quiz10.vo.SearchRes;
import com.example.quiz10.vo.StatisticsRes;
import com.example.quiz10.vo.StatisticsVo;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuesDao quesDao;

	@Autowired
	private FeedbackDao feedbackDao;

	@Transactional
	@Override
	public BasicRes create(CreateUpdateReq req) {
		// �򥻪��ݩʧP�_�w��� @Valide
		// �}�l�ɶ�����񵲧��ɶ���
		if (req.getStartDate().isAfter(req.getEndDate())) {
			return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
		}
		// �P�_���D���A�D��r�ɡA�ﶵ�n����
		List<Ques> quesList = req.getQuesList();
		for (Ques item : quesList) {
//			if() {
//				
//			}
			// ���ϥΤW�����g�k�O�]���q req �L�Ӫ��Ѽƭȥi��|����4�ت���
			// �U���P�_�����g�k�N�����O SINGLE �� MULTI �䤤���@
			if (item.getType().equalsIgnoreCase(SelectType.SINGLE.getType())
					|| item.getType().equalsIgnoreCase(SelectType.MULTI.getType())) {
				// �T�w�O��Φh��A�ﶵ�N�����n����
				// �e������ĸ��A��ܧ_�w���N��
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicRes(ResMessage.OPTIONS_ERROR.getCode(), //
							ResMessage.OPTIONS_ERROR.getMessage());
				}
			}
		}
		// �]�� Quiz ���� id �OAI �۰ʥͦ����y�����A�n�� quizDao ���� save ��i�H��� id ���ȶǦ^�A
		// �����n Quiz �� Entity ���N��ƫ��A�� int ���ݩ� id
		// �[�W @GeneratedValue(strategy = GenerationType.IDENTITY)
		Quiz res = quizDao.save(
				new Quiz(req.getName(), req.getDescription(), req.getStartDate(), req.getEndDate(), req.isPublished()));
		// �N��^�� res ���� id (quiz_id)��� Ques ���� quizId ���ݩʤW
		quesList.forEach(item -> {
			item.setQuizId(res.getId());
		});
		quesDao.saveAll(req.getQuesList());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Transactional
	@Override
	public BasicRes update(CreateUpdateReq req) {
		// �򥻪��ݩʧP�_�w��� @Valide
		// �}�l�ɶ�����񵲧��ɶ���
		if (req.getStartDate().isAfter(req.getEndDate())) {
			return new BasicRes(ResMessage.DATE_ERROR.getCode(), ResMessage.DATE_ERROR.getMessage());
		}
		// �P�_���D���A�D��r�ɡA�ﶵ�n����
		List<Ques> quesList = req.getQuesList();
		for (Ques item : quesList) {
			// �ˬd���D���� quizId �M�ݨ����� id �O�_�ۦP
			if (item.getQuizId() != req.getId()) {
				return new BasicRes(ResMessage.QUIZ_ID_NOT_MATCH.getCode(), ResMessage.QUIZ_ID_NOT_MATCH.getMessage());
			}
//					if() {
//						
//					}
			// ���ϥΤW�����g�k�O�]���q req �L�Ӫ��Ѽƭȥi��|����4�ت���
			// �U���P�_�����g�k�N�����O SINGLE �� MULTI �䤤���@
			if (item.getType().equalsIgnoreCase(SelectType.SINGLE.getType())
					|| item.getType().equalsIgnoreCase(SelectType.MULTI.getType())) {
				// �T�w�O��Φh��A�ﶵ�N�����n����
				// �e����ĸ���_�w���N��
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicRes(ResMessage.OPTIONS_ERROR.getCode(), //
							ResMessage.OPTIONS_ERROR.getMessage());
				}
			}
		}
		// �ˬd�n�ק諸�ݨ��O�_�s�b
		if (!quizDao.existsById(req.getId())) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), //
					ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		quizDao.save(new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(), req.getEndDate(),
				req.isPublished()));
		// �R�����i�ݨ��Ҧ����D
		quesDao.deleteByQuizId(req.getId());
		// �s�W��s�᪺���D
		quesDao.saveAll(req.getQuesList());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Override
	public BasicRes delete(DeleteReq req) {
		// �i�椤���ݨ�����R���G��X�n�R���� idList ���O�_���]�t�i�椤���ݨ�
		// �i�椤���ݨ�����G1.�w�o�G �B 2.��e�ɶ� >= �}�l��� �B 3.��e�ɶ� <= �������
		boolean res = quizDao.existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
				req.getQuesIdList(), LocalDate.now(), LocalDate.now());
		if (res) { // ���P�� res == true�A��ܭn�R�����ݨ�ID�������b�i�椤��
			return new BasicRes(ResMessage.QUIZ_IN_PROGRESS.getCode(), ResMessage.QUIZ_IN_PROGRESS.getMessage());
		}
		quizDao.deleteAllById(req.getQuesIdList());
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Override
	public SearchRes search(SearchReq req) {
		String quizName = req.getQuizName();
		LocalDate startDate = req.getStartDate();
		LocalDate endDate = req.getEndDate();
		if (!StringUtils.hasText(quizName)) {
			// quizName ���a�ȮɡA�N��N�O�D�j�M���󤧤@�A�ҥH��ȥi��|�O�Ŧr��� null
			// ���ťդ]���O�D�j�M���󤧤@
			// �|�� quizName �ܦ��Ŧr��O�]�� containing�A��j�M�����ȱa�Ŧr��ɡA�|������
			quizName = "";
		}
		if (startDate == null) {
			startDate = LocalDate.of(1970, 1, 1);
		}
		if (endDate == null) {
			endDate = LocalDate.of(2999, 12, 31);
		}
		List<Quiz> res = quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName,
				startDate, endDate);
		List<QuizRes> quizResList = new ArrayList<>();
		// ��k1�G�ϥ� foreach �ھڨC�� quizId �h�������� Ques
		// ���I�G�̾� Quiz���h��A�M�w�s��DB������ƪ����ơF�ݨ��V�h�A�s��DB�����ƴN�V�h
		for (Quiz item : res) {
			// �ھ�quizId �����ݨ��̪� quesList
			int quizId = item.getId();
			List<Ques> quesList = quesDao.findByQuizId(quizId);
			// �إ� QuizRes �Ψө� Quiz �M������ List<Ques>
			// �Y�ѼƤӦh�ϥΫغc��k�e���V�áA��ĳ�ΥH�U���覡
			QuizRes quizRes = new QuizRes();
			quizRes.setId(quizId);
			quizRes.setName(item.getName());
			quizRes.setDescription(item.getDescription());
			quizRes.setStartDate(item.getStartDate());
			quizRes.setEndDate(item.getEndDate());
			quizRes.setPublished(item.isPublished());
			quizRes.setQuesList(quesList);
			// ��C�i���P���ݨ�+���D��i List<QuizRes> ��
			quizResList.add(quizRes);
		}
		// ��k2�G���`���Ҧ��ŦX quizId�A�A�@�������ŦX���󪺩Ҧ� Ques�F���ްݨ��h��A�N�u�|�s��DB 2��
		List<Integer> quizIdList = new ArrayList<>();
		for (Quiz item : res) {
			quizIdList.add(item.getId());
		}
		List<Ques> quesList = quesDao.findByQuizIdIn(quizIdList);
		// �N�ŦX�j�M���� res (�Ҧ��ݨ�) �M quesList (�Ҧ����D) �t��
		List<QuizRes> quizResList2 = new ArrayList<>();
		for (Quiz item : res) {
			int quizId = item.getId();
			List<Ques> returnQuesList = new ArrayList<>();
			for (Ques quesItem : quesList) {
				// �P�_ Quiz �M Ques ���� quizId �O�_�ۦP
				if (quizId == quesItem.getQuizId()) {
					// quizId�@�ˡA�N�[�i
					returnQuesList.add(quesItem);
				}
			}
			QuizRes quizRes = new QuizRes();
			quizRes.setId(quizId);
			quizRes.setName(item.getName());
			quizRes.setDescription(item.getDescription());
			quizRes.setStartDate(item.getStartDate());
			quizRes.setEndDate(item.getEndDate());
			quizRes.setPublished(item.isPublished());
			quizRes.setQuesList(returnQuesList);
			// ��C�i���P���ݨ�+���D��i List<QuizRes> ��
			quizResList2.add(quizRes);
		}
		return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), quizResList);
	}

	@Override
	public BasicRes fillin(FillinReq req) {
		// ���ˬd req ���� List<Feedback>�A�Ҧ� Feedback �� quizId �M email ���O�@�˪�
		// �N�O quizId �M email �u�|���@��
		List<Feedback> feedbackList = req.getFeedbackList();
		Set<Integer> quizIdSet = new HashSet<>();
		Set<String> emailSet = new HashSet<>();
		for (Feedback item : feedbackList) {
			quizIdSet.add(item.getQuizId());
			emailSet.add(item.getEmail());
		}
		// �]�� set ���Ȥ��|���ơA�ҥH��� set ���j�p�����O 1 ���ܴN��� quizId �� email ���@�P
		if (quizIdSet.size() != 1 || emailSet.size() != 1) {
			return new BasicRes(ResMessage.QUIZ_ID_OR_EMAIL_INCONSISTENT.getCode(), //
					ResMessage.QUIZ_ID_OR_EMAIL_INCONSISTENT.getMessage());
		}
		int quizId = req.getFeedbackList().get(0).getQuizId();
		// �ˬd�P�@�� email + �O�_�w�s�b(�P�@�� email �w�g��g�L�P�@�i�ݨ�)
		if (feedbackDao.existsByQuizIdAndEmail(quizId, req.getFeedbackList().get(0).getEmail())) {
			return new BasicRes(ResMessage.EMAIL_DUPLICATE.getCode(), //
					ResMessage.EMAIL_DUPLICATE.getMessage());
		}
		// �ˬd�ݨ��O�_�i�H��g�����A�G1. �w�o�� 2. ��e�ɶ� >= �}�l�ɶ� 3. ��e�ɶ� <= �����ɶ�
		// �e�����I�A��ܧ䤣��w�o���B��e�ɶ��O����}�l�ɶ��P�����ɶ����������
		if (!quizDao.existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(//
				List.of(quizId), LocalDate.now(), LocalDate.now())) {
			return new BasicRes(ResMessage.CANNOT_FILLIN_QUIZ.getCode(), //
					ResMessage.CANNOT_FILLIN_QUIZ.getMessage());
		}
		// �ˬd����
		List<Ques> quesList = quesDao.findByQuizId(quizId);
		// �ˬd���D�M���׵��ƬO�_�@��
		if (feedbackList.size() != quesList.size()) {
			return new BasicRes(ResMessage.FILLIN_INCOMPLETE.getCode(), //
					ResMessage.FILLIN_INCOMPLETE.getMessage());
		}
		//
		Set<Integer> necessaryQuIds = new HashSet<>();
		Set<Integer> singleQuIds = new HashSet<>();
		Set<Integer> quIds = new HashSet<>();
		for (Ques quesItem : quesList) {
			if (quesItem.isNecessary()) {
				necessaryQuIds.add(quesItem.getId());
			}
			if (quesItem.getType().equalsIgnoreCase(SelectType.SINGLE.getType())) {
				singleQuIds.add(quesItem.getId());
			}
			quIds.add(quesItem.getId());
		}
		Map<Integer, List<String>> quIdAnsMap = new HashMap<>();
		for (Feedback item : feedbackList) {
			int quId = item.getQuId();
			// �ˬd���ת��D���O�_�P���D�D���۲�
			if (!quIds.contains(quId)) { // �e�����I��ܵ����D�����]�t�b���D�D����
				return new BasicRes(ResMessage.QUID_MISMATCH.getCode(), //
						ResMessage.QUID_MISMATCH.getMessage());
			}
			// �ˬd����B�n������
			// ������S����
			if (necessaryQuIds.contains(quId) && !StringUtils.hasText(item.getAns())) {
				return new BasicRes(ResMessage.FILLIN_IS_NECESSARY.getCode(), //
						ResMessage.FILLIN_IS_NECESSARY.getMessage());
			}
			// �ˬd���B����ɡA���঳�h�ӵ���
			List<String> ansList = item.getAns() == null ? List.of("") : List.of(item.getAns().split(";"));
			if (singleQuIds.contains(quId) && ansList.size() > 1) {
				return new BasicRes(ResMessage.SINGLE_CHOICE_QUES.getCode(), //
						ResMessage.SINGLE_CHOICE_QUES.getMessage());
			}
			// ���`���������D���P���������סA�Ψӫ����ﵪ�׻P�ﶵ��
			quIdAnsMap.put(quId, ansList);
		}
		// �ˬd�D��r�ɡA���׻P�ﶵ�n�@�P
		for (Ques item : quesList) {
			String type = item.getType();
			if (!type.equalsIgnoreCase(SelectType.TEXT.getType())) {
				// quIdAnsMap.get(quId)�G�O�ھ� key �Ө��o map �������� value
				List<String> ansList = quIdAnsMap.get(item.getId());
				List<String> optionList = List.of(item.getOptions().split(";"));
				
				System.out.println("Question ID: " + item.getId());
		        System.out.println("Options: " + optionList);
		        System.out.println("Answers: " + ansList);
				// �ۦP�����D�A�P�_���׬O�_�P�ﶵ�@�P
				if (!optionList.containsAll(ansList)) {
					return new BasicRes(ResMessage.OPTION_ANSWER_MISMATCH.getCode(), //
							ResMessage.OPTION_ANSWER_MISMATCH.getMessage());
				}
			}
		}
		feedbackDao.saveAll(feedbackList);
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Override
	public StatisticsRes statistics(int quizId) {
		// ������ quiz ��������T
		Optional<Quiz> op = quizDao.findById(quizId);
		if (op.isEmpty()) {
			return new StatisticsRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		String quizName = quiz.getName();
		// ���q ques �����D��r���������D(��r���������D���C�J�έp)
		List<Ques> quesList = quesDao.findByQuizIdAndTypeNot(quizId, SelectType.TEXT.getType());
		// �`�����Φh����D�� Id�A����ΨӼ��� feedback ����ƥ�
		List<Integer> quIdList = new ArrayList<>();
		// ���D�s�� �ﶵ ����
		Map<Integer, Map<String, Integer>> quIdOptionCountMap = new HashMap<>();
		List<StatisticsVo> statisticsList = new ArrayList<>();
		for (Ques item : quesList) {
			quIdList.add(item.getId());
			Map<String, Integer> optionCountMap = new HashMap<>();
			List<String> optionList = List.of(item.getOptions().split(";"));
			for (String option : optionList) {
				optionCountMap.put(option, 0);
			}
			quIdOptionCountMap.put(item.getId(), optionCountMap);
			StatisticsVo vo = new StatisticsVo();
			vo.setQuId(item.getId());
			vo.setQu(item.getQu());
			statisticsList.add(vo);
		}
		// ====================================
		// �W����for�j�����H��|�����H�U����
		// ���D�s�� �ﶵ ����
		// Map<Integer, Map<String, Integer>> quIdOptionCountMap = new HashMap<>();
		// 1 �J�� 0
		// 1 �R�q 0
		// 1 �~�� 0
		// 1 ���{ 0
		// �H�W�ۦP key ��ơA�u�|���@���A�N�O���D�s�� 1�A������ value ���� map �N�|��4�Ӹ��
		// �g�@��test�h���էY�i�o����榡
		// ====================================
		//
		List<Feedback> feedbackList = feedbackDao.findByQuizIdAndQuIdIn(quizId, quIdList);
		// ==============================================
		for (Feedback item : feedbackList) {
			// �ھڰ��D�s���� �ﶵ���ƪ� map �q quIdOptionCountMap �����X
			Map<String, Integer> optionCountMap = quIdOptionCountMap.get(item.getQuId());
			// �Nfeedback����ans�r���নList
			List<String> ansList = List.of(item.getAns().split(";"));
			for (String ans : ansList) {
				// �ھڿﶵ���X����������
				int count = optionCountMap.get(ans);
				// �N�쥻������+1
				count++;
				// ��ۦP�� �ﶵ�B���� ��^��map��
				optionCountMap.put(ans, count);
			}
			// ��s quIdOptionCountMap �������
			quIdOptionCountMap.put(item.getQuId(), optionCountMap);
		}
		for (StatisticsVo item : statisticsList) {
			int quId = item.getQuId();
			Map<String, Integer> optionCountMap = quIdOptionCountMap.get(quId);
			item.setOptionCountMap(optionCountMap);
			// �W��3��{���X�i�ΤU���@����
			// item.setOptionCountMap(quIdOptionCountMap.get(item.getQuId())); �ΦW�A�ΦW
		}
		return new StatisticsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), //
				quizName, statisticsList);
		// ==============================================
//		for (Feedback item : feedbackList) {
//			StatisticsVo vo = new StatisticsVo();
//			Map<String, Integer> optionCountMap = new HashMap<>();
//			List<String> ansList = List.of(item.getAns().split(";"));
//			for (String ans : ansList) {
//				// �P�_ map ���O�_�w���ﶵ(key)
//				if (optionCountMap.containsKey(ans)) {
//					// �ﶵ�w�s�b map ���A�N����������ƨ��X
//					int count = optionCountMap.get(ans);
//					// �N�쥻������ + 1
//					count++;
//					// ��ۦP�� �ﶵ ���Ʃ�^��map��
//					optionCountMap.put(ans, count);
//					// ���L���j��A�����{���~�򩹤U����
//					continue;
//				}
//				optionCountMap.put(ans, 1);
//			}
//		}
//		return null;
	}

}
