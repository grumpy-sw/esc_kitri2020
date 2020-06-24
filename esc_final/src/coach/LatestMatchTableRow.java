package coach;

import javafx.beans.property.StringProperty;

public class LatestMatchTableRow {
	private StringProperty date;
	private StringProperty league;
	private StringProperty champ;
	private StringProperty winlose;
	private StringProperty kda;
	private StringProperty kRate;
	
	public LatestMatchTableRow(StringProperty date,
			StringProperty league,
			StringProperty champ,
			StringProperty winlose,
			StringProperty kda,
			StringProperty kRate) {
		this.date = date;
		this.league = league;
		this.champ = champ;
		this.winlose = winlose;
		this.kda = kda;
		this.kRate = kRate;
	}
	
	
	public StringProperty dateProperty() {
		return date;
	}
	public StringProperty leagueProperty() {
		return league;
	}
	public StringProperty champProperty() {
		return champ;
	}
	public StringProperty winloseProperty() {
		return winlose;
	}
	public StringProperty kdaProperty() {
		return kda;
	}
	public StringProperty kRateProperty() {
		return kRate;
	}
	
	
	
}
