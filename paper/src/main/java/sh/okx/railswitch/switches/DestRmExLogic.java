package sh.okx.railswitch.switches;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;

import com.google.re2j.Pattern;
import com.google.re2j.Matcher;
import org.bukkit.entity.Player;

import sh.okx.railswitch.settings.SettingsManager;

/**
 * Logic for removing destinations via regular expressions
 */
public class DestRmExLogic extends SwitchLogic {
	public static final Pattern DESTEX = Pattern.compile("\\[destrmex(?:;(\\w*))?\\]", Pattern.CASE_INSENSITIVE);

	public final Pattern pattern;
	//public final boolean multi_match;

	public DestRmExLogic(String[] lines, Matcher match) throws Exception {
		super();
		pattern = DestExUtils.compilePattern(Arrays.copyOfRange(lines, 1, lines.length), match.group(1));
		//this.multi_match = pattern.flags() & Pattern.MULTILINE > 0;
	}

	@Override
	public boolean decide(Player player) {
		boolean didSomething = false;
		ArrayList<String> dests = new ArrayList();
		String destString = SettingsManager.getDestination(player);
		Collections.addAll(dests, StringUtils.split(destString, " "));

		Iterator<String> iter = dests.iterator();
		while (iter.hasNext()) {
			String dest = iter.next();
			if (dest.isBlank()) continue;

			Matcher matcher = pattern.matcher(dest);
			if (matcher.matches()) {
				iter.remove();
				didSomething = true;
			}
		}

		if (didSomething) {
			SettingsManager.setDestination(player, String.join(" ", dests));
			return true;
		}
		return false;
	}
}
