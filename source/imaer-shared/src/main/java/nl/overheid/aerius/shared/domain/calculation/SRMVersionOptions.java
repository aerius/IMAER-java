package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;

public class SRMVersionOptions implements Serializable {

  private static final long serialVersionUID = 1L;

  private String preSRMVersion;
  private String luVersion;
  private String depositionVelocityVersion;
  private String windveldenVersion;

  public String getPreSRMVersion() {
    return preSRMVersion;
  }

  public void setPreSRMVersion(final String preSRMVersion) {
    this.preSRMVersion = preSRMVersion;
  }

  public String getLuVersion() {
    return luVersion;
  }

  public void setLuVersion(final String luVersion) {
    this.luVersion = luVersion;
  }

  public String getWindveldenVersion() {
    return windveldenVersion;
  }

  public void setWindveldenVersion(final String windveldenVersion) {
    this.windveldenVersion = windveldenVersion;
  }

  public String getDepositionVelocityVersion() {
    return depositionVelocityVersion;
  }

  public void setDepositionVelocityVersion(final String depositionVelocityVersion) {
    this.depositionVelocityVersion = depositionVelocityVersion;
  }

}
