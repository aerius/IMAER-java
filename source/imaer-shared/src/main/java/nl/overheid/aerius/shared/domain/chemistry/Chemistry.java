package nl.overheid.aerius.shared.domain.chemistry;

public enum Chemistry {

  PROGNOSIS("prognosis"),
  ACTUAL("");

  private final String opsLabel;

  Chemistry(final String opsLabel) {
    this.opsLabel = opsLabel;
  }

  public String getOpsLabel() {
    return opsLabel;
  }
}
