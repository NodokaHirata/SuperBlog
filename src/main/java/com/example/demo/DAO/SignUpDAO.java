package com.example.demo.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import com.example.demo.DTO.USER;
@Repository // DAOクラスに付与するアノテーション
public class SignUpDAO {

	@Autowired
    private NamedParameterJdbcTemplate NamedParameterJdbcTemplate; // DB接続できるインスタンス

	//ユーザーID重複チェック
	public boolean uniqueChecker(USER param) {
		StringBuffer query = new StringBuffer();
			query.append("SELECT");
			query.append(" COUNT(*) AS count");
			query.append(" FROM");
			query.append(" USER");
			query.append(" WHERE 1 = 1");
			if (!StringUtils.isEmpty(param.getUserId())){
				String user_id = param.getUserId();
				query.append(" AND USER_ID = '" + user_id + "'");
			}
			if (!StringUtils.isEmpty(param.getUserPWD())) {
				String user_pwd = param.getUserPWD();
				query.append(" AND USER_PWD = '" + user_pwd + "'");
			}
		// jdbcTemplateの機能によってDBに対しSQLを実行。
		SqlParameterSource params = new MapSqlParameterSource();
		List<Map<String, Object>> count = NamedParameterJdbcTemplate.queryForList(query.toString(), params);
		//重複するユーザーIDがある場合false
		if(!("0".equals(String.valueOf(count.get(0).get("count"))))) {
			return false;
		}else {
			return true;
		}
	}


	//登録
	public void regist(USER param){
		StringBuffer query = new StringBuffer();
		//Insert
		query.append("INSERT INTO USER ");
		query.append("(USER_ID,USER_PWD) ");
		query.append("VALUES ('" + param.getUserId() + "','" + param.getUserPWD() + "')");
		// jdbcTemplateの機能によってDBに対しSQLを実行。

		SqlParameterSource data = new MapSqlParameterSource();
		NamedParameterJdbcTemplate.update(query.toString(), data);
	}

}
