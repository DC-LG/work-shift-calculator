package persistence;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created on: 2/16/20
 *
 * @author Denis Citaku
 **/
@Table(name = "user_config")
@Entity
@Data
public class UserConfig {

  @Id
  private Long id;

  private String currency;

  private String locale;

  private Boolean autoIncludeShiftsFromEmail;

  private String emailPassword;

  @OneToOne
  @MapsId
  private User user;

}
