package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单
 */
public class PmsMenu implements Serializable {

	// Fields

	private static final long serialVersionUID = -7790273275206988348L;
	private String menuId;
	private String menuName;
	private String menuParentId;
	private String menuUrl;
	private Integer menuSort;
	private Integer menuIsdel;
	private String menuModule;
	private Integer menuNeedConfirm;
	private Integer menuDepth;
	private Date menuCreateDate;
	private Date menuUpdateDate;
	private Integer menuCreateUser;
	private Integer menuUpdateUser;

	// Constructors

	/** default constructor */
	public PmsMenu() {
	}

	/** minimal constructor */
	public PmsMenu(String menuId) {
		this.menuId = menuId;
	}

	/** full constructor */
	public PmsMenu(String menuId, String menuName, String menuParentId,
			String menuUrl, Integer menuSort, Integer menuIsdel,
			String menuModule, Integer menuNeedConfirm,Integer menuDepth,
			Date menuCreateDate, Date menuUpdateDate,
			Integer menuCreateUser, Integer menuUpdateUser) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuParentId = menuParentId;
		this.menuUrl = menuUrl;
		this.menuSort = menuSort;
		this.menuIsdel = menuIsdel;
		this.menuModule = menuModule;
		this.menuNeedConfirm = menuNeedConfirm;
		this.menuDepth = menuDepth;
		this.menuCreateDate = menuCreateDate;
		this.menuUpdateDate = menuUpdateDate;
		this.menuCreateUser = menuCreateUser;
		this.menuUpdateUser = menuUpdateUser;
	}

	// Property accessors

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuParentId() {
		return this.menuParentId;
	}

	public void setMenuParentId(String menuParentId) {
		this.menuParentId = menuParentId;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public Integer getMenuIsdel() {
		return this.menuIsdel;
	}

	public void setMenuIsdel(Integer menuIsdel) {
		this.menuIsdel = menuIsdel;
	}

	public String getMenuModule() {
		return this.menuModule;
	}

	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}

	public Integer getMenuNeedConfirm() {
		return this.menuNeedConfirm;
	}

	public void setMenuNeedConfirm(Integer menuNeedConfirm) {
		this.menuNeedConfirm = menuNeedConfirm;
	}

	public Integer getMenuDepth() {
		return menuDepth;
	}

	public void setMenuDepth(Integer menuDepth) {
		this.menuDepth = menuDepth;
	}

	public Date getMenuCreateDate() {
		return this.menuCreateDate;
	}

	public void setMenuCreateDate(Date menuCreateDate) {
		this.menuCreateDate = menuCreateDate;
	}

	public Date getMenuUpdateDate() {
		return this.menuUpdateDate;
	}

	public void setMenuUpdateDate(Date menuUpdateDate) {
		this.menuUpdateDate = menuUpdateDate;
	}

	public Integer getMenuCreateUser() {
		return this.menuCreateUser;
	}

	public void setMenuCreateUser(Integer menuCreateUser) {
		this.menuCreateUser = menuCreateUser;
	}

	public Integer getMenuUpdateUser() {
		return this.menuUpdateUser;
	}

	public void setMenuUpdateUser(Integer menuUpdateUser) {
		this.menuUpdateUser = menuUpdateUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((menuCreateDate == null) ? 0 : menuCreateDate.hashCode());
		result = prime * result
				+ ((menuCreateUser == null) ? 0 : menuCreateUser.hashCode());
		result = prime * result
				+ ((menuDepth == null) ? 0 : menuDepth.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result
				+ ((menuIsdel == null) ? 0 : menuIsdel.hashCode());
		result = prime * result
				+ ((menuModule == null) ? 0 : menuModule.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result
				+ ((menuNeedConfirm == null) ? 0 : menuNeedConfirm.hashCode());
		result = prime * result
				+ ((menuParentId == null) ? 0 : menuParentId.hashCode());
		result = prime * result
				+ ((menuSort == null) ? 0 : menuSort.hashCode());
		result = prime * result
				+ ((menuUpdateDate == null) ? 0 : menuUpdateDate.hashCode());
		result = prime * result
				+ ((menuUpdateUser == null) ? 0 : menuUpdateUser.hashCode());
		result = prime * result + ((menuUrl == null) ? 0 : menuUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PmsMenu other = (PmsMenu) obj;
		if (menuCreateDate == null) {
			if (other.menuCreateDate != null)
				return false;
		} else if (!menuCreateDate.equals(other.menuCreateDate))
			return false;
		if (menuCreateUser == null) {
			if (other.menuCreateUser != null)
				return false;
		} else if (!menuCreateUser.equals(other.menuCreateUser))
			return false;
		if (menuDepth == null) {
			if (other.menuDepth != null)
				return false;
		} else if (!menuDepth.equals(other.menuDepth))
			return false;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		if (menuIsdel == null) {
			if (other.menuIsdel != null)
				return false;
		} else if (!menuIsdel.equals(other.menuIsdel))
			return false;
		if (menuModule == null) {
			if (other.menuModule != null)
				return false;
		} else if (!menuModule.equals(other.menuModule))
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (menuNeedConfirm == null) {
			if (other.menuNeedConfirm != null)
				return false;
		} else if (!menuNeedConfirm.equals(other.menuNeedConfirm))
			return false;
		if (menuParentId == null) {
			if (other.menuParentId != null)
				return false;
		} else if (!menuParentId.equals(other.menuParentId))
			return false;
		if (menuSort == null) {
			if (other.menuSort != null)
				return false;
		} else if (!menuSort.equals(other.menuSort))
			return false;
		if (menuUpdateDate == null) {
			if (other.menuUpdateDate != null)
				return false;
		} else if (!menuUpdateDate.equals(other.menuUpdateDate))
			return false;
		if (menuUpdateUser == null) {
			if (other.menuUpdateUser != null)
				return false;
		} else if (!menuUpdateUser.equals(other.menuUpdateUser))
			return false;
		if (menuUrl == null) {
			if (other.menuUrl != null)
				return false;
		} else if (!menuUrl.equals(other.menuUrl))
			return false;
		return true;
	}
}