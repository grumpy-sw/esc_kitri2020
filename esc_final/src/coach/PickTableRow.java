package coach;

import javafx.beans.property.StringProperty;

public class PickTableRow {
	private StringProperty p_champion;
	private StringProperty p_games;
	private StringProperty p_win;
	private StringProperty p_lose;
	private StringProperty p_winRate;
	
	public PickTableRow(StringProperty p_champion, StringProperty p_games, StringProperty p_win, StringProperty p_lose, StringProperty p_winRate) {
		this.p_champion = p_champion;
		this.p_games = p_games;
		this.p_win = p_win;
		this.p_lose = p_lose;
		this.p_winRate = p_winRate;
		
	}
	public StringProperty p_championProperty() {
		return p_champion;
	}
	public StringProperty p_gamesProperty() {
		return p_games;
	}
	public StringProperty p_winProperty() {
		return p_win;
	}
	public StringProperty p_loseProperty() {
		return p_lose;
	}
	public StringProperty p_winRateProperty() {
		return p_winRate;
	}
	

}
