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

import com.example.demo.DTO.TBL_POST;

@Repository // DAOクラスに付与するアノテーション
public class PostDAO {
	@Autowired
    private NamedParameterJdbcTemplate NamedParameterJdbcTemplate; // DB接続できるインスタンス

	//blog検索
	public List<TBL_POST> search(TBL_POST condition){
		StringBuffer query = new StringBuffer();
        //SELECT文を生成
		//検索条件なければ全件
		query.append("SELECT * FROM TBL_POST");
		//検索条件がある場合
		if (condition != null) {
			query.append(" WHERE 1=1");
			if (condition.getPostNo() != 0) {
				query.append(" AND POST_NO = " + condition.getPostNo());
			}else if (condition.getSearchText() != null) {
				query.append(" AND POST_TITLE LIKE '%" + condition.getSearchText() + "%' "
							+ "OR POST_TEXT LIKE '%" + condition.getSearchText() + "%'");
			}
		}
		// jdbcTemplateの機能によってDBに対しSQLを実行。
		SqlParameterSource params = new MapSqlParameterSource();
		List<TBL_POST> list = new ArrayList<TBL_POST>();
		List<Map<String, Object>> post = NamedParameterJdbcTemplate.queryForList(query.toString(), params);
        if (!post.isEmpty()) {
        	for(int i=0; i<post.size(); i++) {
        		TBL_POST obj = new TBL_POST();
        		obj.setInstDate((LocalDateTime) post.get(i).get("INST_DATE"));
        		obj.setInstUser(String.valueOf(post.get(i).get("INST_USER")));
        		obj.setUpdtDate((LocalDateTime) post.get(i).get("UPDT_DATE"));
        		obj.setUpdtUser(String.valueOf(post.get(i).get("UPDT_USER")));
        		//obj.setDelFgl((int) post.get(i).get("DEL_FLG"));
        		obj.setPostNo((int) post.get(i).get("POST_NO"));
        		obj.setPostTitle(String.valueOf(post.get(i).get("POST_TITLE")));
        		obj.setPostText(String.valueOf(post.get(i).get("POST_TEXT")));
        		list.add(obj);
        	}
        }
		return list;
	}

	//blogインサート
	public void insert(TBL_POST data){
		StringBuffer query = new StringBuffer();
		//INSERT文を作成
		query.append("INSERT INTO TBL_POST ");
		query.append("(INST_USER,POST_TITLE,POST_TEXT )");
		query.append("VALUES ('" + data.getInstUser() + "','" + data.getPostTitle() + "','" + data.getPostText() + "')");


		// jdbcTemplateの機能によってDBに対しSQLを実行。
		SqlParameterSource params = new MapSqlParameterSource();
		NamedParameterJdbcTemplate.update(query.toString(), params);
	}
}
