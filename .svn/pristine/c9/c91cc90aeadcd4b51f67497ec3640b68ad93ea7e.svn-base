package banger.moduleIntf;

import banger.domain.customer.CustPotentialCustomersFiles;

import java.util.List;

public interface IPotentialCustomerFileProvider {
    /**
     * 保存一条附件信息
     * @param info
     * @param loginUserId
     */
    void insertPotentialCustomersFiles(CustPotentialCustomersFiles info, Integer loginUserId);

    /**
     * 根据潜在客户id获取该客户的影像资料
     * @param potentialId
     * @return
     */
    List<CustPotentialCustomersFiles> getPotentialFilesListByPotentialId(Integer potentialId);


    /**
     * 根据主键获取
     * @param id
     * @return
     */
    CustPotentialCustomersFiles getPotentialCustomersFilesById(Integer id);


    /**
     * 根据主键获取
     * @param fileId
     * @return
     */
    CustPotentialCustomersFiles getPotentialCustomersFilesByFileId(String fileId);
}
