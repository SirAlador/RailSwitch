package sh.okx.railswitch.switches;

import com.google.re2j.Matcher;
import org.bukkit.entity.Player;

/**
 * Base class for switch decisions, and a function to determine which subclass to use
 */
public abstract class SwitchLogic {
	public SwitchLogic() throws Exception {

	}

	abstract boolean decide(Player player) throws Exception;

	public static SwitchLogic try_create(String[] lines) throws Exception {
		//Simple destinations
		if (lines[0].equalsIgnoreCase(SimpleDestLogic.NORMAL) || lines[0].equalsIgnoreCase(SimpleDestLogic.INVERTED))
			return new SimpleDestLogic(lines);

		//Regex destinations
		Matcher destex = DestExLogic.DESTEX.matcher(lines[0]);
		if (destex.matches()) return new DestExLogic(lines, destex);

		//Destination adding
		if (lines[0].equalsIgnoreCase(AddDestLogic.INVOCATION)) return new AddDestLogic(lines);

		//Destination removal
		if (lines[0].equalsIgnoreCase(RemoveDestLogic.INVOCATION)) return new RemoveDestLogic(lines);

		//Destination removal via regex
		Matcher destexrm = RemoveDestExLogic.DESTEX.matcher(lines[0]);
		if (destexrm.matches()) return new RemoveDestExLogic(lines, destexrm);

		//Failure to specify the [] bits
		if (lines[0].length() >= 2 && lines[0].charAt(0) == '[' && lines[0].charAt(lines[0].length() - 1) == ']') {
			throw new RuntimeException("Unknown logic specifier");
		}
		
		return null;
	}

}