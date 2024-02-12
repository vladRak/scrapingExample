package scraping.dto.sport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class SportsWrapper<T extends List<Sport>> {

	Map<String, Sport> sportsMap;

	public SportsWrapper(List<Sport> sports) {
		this.sportsMap = new HashMap<String, Sport>();
		sports.forEach(sport -> {
			sportsMap.put(sport.getName(), sport);
		});
	}

	public Collection<Sport> getSports() {
		return sportsMap.values();
	}

	public List<Sport> getSports(Set<String> sportNames) {
		List<Sport> result = new ArrayList<Sport>();
		sportNames.forEach(name -> {
			result.add(sportsMap.get(name));
		});
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sportsMap);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SportsWrapper<?> other = (SportsWrapper<?>) obj;
		return Objects.equals(sportsMap, other.sportsMap);
	}

}
