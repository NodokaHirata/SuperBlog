package com.example.demo.DAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.DTO.TBL_ANSWERS;

@Repository // DAOクラスに付与するアノテーション
public class AnswerDAO {
	@Autowired
    private NamedParameterJdbcTemplate NamedParameterJdbcTemplate; // DB接続できるインスタンス


	//Inset Answers
	public void insert(TBL_ANSWERS data){
		StringBuffer query = new StringBuffer();
		//INSERT文を作成
		query.append("INSERT INTO TBL_ANSWERS ");
		query.append("(INST_USER,ANSWER_TITLE,ANSWER_TEXT,ANSWER_NO )");
		query.append("VALUES ('" + data.getInstUser() + "','" + data.getAnswerTitle() + "','" + data.getAnswerText() + "','" + data.getAnswerNo() + "')");


		// jdbcTemplateの機能によってDBに対しSQLを実行。
		SqlParameterSource params = new MapSqlParameterSource();
		NamedParameterJdbcTemplate.update(query.toString(), params);
	}

	//Get Answers
	public List<TBL_ANSWERS> getAnswers(TBL_ANSWERS data){
		StringBuffer query = new StringBuffer();
		//INSERT文を作成
		query.append("SELECT * FROM TBL_ANSWERS ");
		query.append("WHERE ANSWER_NO =" + data.getAnswerNo());


		// jdbcTemplateの機能によってDBに対しSQLを実行。
		SqlParameterSource params = new MapSqlParameterSource();
		List<TBL_ANSWERS> list = new ArrayList<TBL_ANSWERS>();
		List<Map<String, Object>> answers = NamedParameterJdbcTemplate.queryForList(query.toString(), params);
		if (!answers.isEmpty()) {
			for (int i=0; i<answers.size(); i++) {
				TBL_ANSWERS obj = new TBL_ANSWERS();
				obj.setInstDate((LocalDateTime)answers.get(i).get("INST_DATE"));
				obj.setInstUser(String.valueOf(answers.get(i).get("INST_USER")));
				obj.setUpdtDate((LocalDateTime) answers.get(i).get("UPDT_DATE"));
        		obj.setUpdtUser(String.valueOf(answers.get(i).get("UPDT_USER")));
        		//obj.setDelFgl((int) answers.get(i).get("DEL_FLG"));
        		obj.setAnswerNo((int) answers.get(i).get("ANSWER_NO"));
        		obj.setAnswerTitle(String.valueOf(answers.get(i).get("ANSWER_TITLE")));
        		obj.setAnswerText(String.valueOf(answers.get(i).get("ANSWER_TEXT")));
        		list.add(obj);
			}
		}
		return list;
	}
}
