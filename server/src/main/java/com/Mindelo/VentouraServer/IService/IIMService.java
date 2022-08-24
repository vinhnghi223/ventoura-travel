package com.Mindelo.VentouraServer.IService;



import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;

import com.Mindelo.VentouraServer.Enum.UserRole;

public interface IIMService {

	public XMPPConnection getConnection(String domain) throws Exception;

	public XMPPConnection getConnection(String domain, int port, String service)
			throws Exception;
	
	public boolean login(XMPPConnection connection, String username, String password);
	
	

	public void sendMessage(XMPPConnection connection, String chattingPartnerIMAccountname, String content);
	
	/**
	 * send message to a specific user
	 */
	public void sendMessage(XMPPConnection imConnection, long userId, UserRole userRole, String content);

	/**
	 * 注册用户
	 * 
	 * @param connection
	 * @param regUserName
	 * @param regUserPwd
	 * @return
	 */
	public boolean createAccount(XMPPConnection connection, String regUserName,
			String regUserPwd);

	/**
	 * 删除当前用户
	 * 
	 * @param connection
	 * @return
	 */
	public boolean deleteAccount(XMPPConnection connection);

	/**
	 * 删除修改密码
	 * 
	 * @param connection
	 * @return
	 */
	public boolean changePassword(XMPPConnection connection, String pwd);

	/**
	 * 返回所有组信息 <RosterGroup>
	 * 
	 * @return List(RosterGroup)
	 */
	public List<RosterGroup> getGroups(Roster roster);

	/**
	 * 返回相应(groupName)组里的所有用户<RosterEntry>
	 * 
	 * @return List(RosterEntry)
	 */
	public List<RosterEntry> getEntriesByGroup(Roster roster, String groupName);

	/**
	 * 返回所有用户信息 <RosterEntry>
	 * 
	 * @return List(RosterEntry)
	 */
	public List<RosterEntry> getAllEntries(Roster roster);


	/**
	 * 添加一个组
	 */
	public boolean addGroup(Roster roster, String groupName);

	/**
	 * 删除一个组
	 */
	public boolean removeGroup(Roster roster, String groupName);

	/**
	 * 添加一个好友 无分组
	 */
	public boolean addUser(Roster roster, String userName, String name);

	/**
	 * 添加一个好友到分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @return
	 */
	public boolean addUser(Roster roster, String userName, String name,
			String groupName);

	/**
	 * 删除一个好友
	 * 
	 * @param roster
	 * @param userName
	 * @return
	 */
	public boolean removeUser(Roster roster, String userName);

	/* Presence Functions */
	public void updateStateToAvailable(XMPPConnection connection) throws NotConnectedException ;

	public void updateStateToUnAvailable(XMPPConnection connection) throws NotConnectedException ;

	public void updateStateToUnAvailableToSomeone(XMPPConnection connection,
			String userName) throws NotConnectedException ;

	public void updateStateToAvailableToSomeone(XMPPConnection connection,
			String userName) throws NotConnectedException ;

	/**
	 * 修改心情
	 * 
	 * @param connection
	 * @param status
	 */
	public void changeStateMessage(XMPPConnection connection, String status) throws NotConnectedException ;
}
