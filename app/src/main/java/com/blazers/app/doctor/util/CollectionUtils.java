package com.blazers.app.doctor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.bmob.im.bean.BmobChatUser;

public class CollectionUtils {

	public static boolean isNotNull(Collection<?> collection) {
		return collection != null && collection.size() > 0;
	}
	
	/** listתmap
	 *  ���û���Ϊkey
	  * @return Map<String,BmobChatUser>
	  * @throws
	  */
	public static Map<String,BmobChatUser> list2map(List<BmobChatUser> users){
		Map<String,BmobChatUser> friends = new HashMap<>();
		for(BmobChatUser user : users){
			friends.put(user.getUsername(), user);
		}
		return friends;
	}
	
	
	/** mapתlist
	  * @Title: map2list
	  * @return List<BmobChatUser>
	  * @throws
	  */
	public static List<BmobChatUser> map2list(Map<String,BmobChatUser> maps){
		List<BmobChatUser> users = new ArrayList<>();
		for (Entry<String, BmobChatUser> entry : maps.entrySet()) {
			users.add(entry.getValue());
		}
		return users;
	}
}
