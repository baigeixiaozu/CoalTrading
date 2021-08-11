package cn.coal.trading.mapper;

import cn.coal.trading.bean.CompanyInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CompanyMapper extends BaseMapper<CompanyInformation> {
//    @Select("select com_name,com,com_intro,legal_name,com_addr,registered_capital,com_contact,fax from ct_company")
    @Select("select * from ct_company where user_id=#{id}")
    List<CompanyInformation> getInfo(Long id);

    @Update("update ct_company set audit_opinion=#{opinion} where user_id=#{id}")
    Boolean Opinion(Long id,String opinion);

    @Update("update ct_company set status=4 where user_id=#{id}")
    Boolean verify(Long id);
    @Update("update ct_company set status=3 where user_id=#{id}")
    Boolean reject(Long id);


    @Insert("INSERT INTO `ct_company` (`user_id`, `${col}`) VALUES (#{uid},#{path})")
    int insertCert(String col, long uid, String path);

    @Insert("UPDATE `ct_company` SET `${col}`=#{path} WHERE `user_id`=#{uid}")
    int updateCert(String col, String path, long uid);

    @Select("select #{name} from ct_company where user_id=#{id}")
    String download(long id,String name);//legal_id_file

    @Select("select legal_id_file from ct_company where user_id=#{id}")
    String legal_id_file(Long id);

    @Select("select business_license_id from ct_company where user_id=#{id}")
    String business_license_id(Long id);
    @Select("select oib_code_file from ct_company where user_id=#{id}")
    String oib_code_file(Long id);
    @Select("select tr_cert_file from ct_company where user_id=#{id}")
    String tr_cert_file(Long id);
    @Select("select manage_license_file from ct_company where user_id=#{id}")
    String manage_license_file(Long id);
}
