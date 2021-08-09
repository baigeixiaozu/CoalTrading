package cn.coal.trading.mapper;

import cn.coal.trading.bean.CompanyInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CompanyMapper extends BaseMapper<CompanyInformation> {
//    @Select("select com_name,com,com_intro,legal_name,com_addr,registered_capital,com_contact,fax from ct_company")
    @Select("select * from ct_company where user_id=#{id}")
    List<CompanyInformation> getInfo(Long id);

    @Update("update ct_company set audit_opinion=#{opinion} where user_id=#{id}")
    void Opinion(Long id,String opinion);

    @Update("update ct_company set status=4 where user_id=#{id}")
    void verify(Long id);
    @Update("update ct_company set status=3 where user_id=#{id}")
    void reject(Long id);
    @Select("select  com_zip from ct_company where id=#{id}")
    String download(long id);

    @Insert("INSERT INTO `ct_company` (`user_id`, `${col}`) VALUES (#{uid},#{path})")
    int insertCert(String col, long uid, String path);

    @Insert("UPDATE `ct_company` SET `${col}`=#{path} WHERE `user_id`=#{uid})")
    int updateCert(String col, String path, long uid);
}
