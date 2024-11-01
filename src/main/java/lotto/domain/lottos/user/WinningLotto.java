package lotto.domain.lottos.user;

import java.util.EnumMap;
import java.util.Map;
import lotto.domain.Rank;

public class WinningLotto {
    private final EnumMap<Rank, Integer> ranks = new EnumMap<>(Rank.class);

    public WinningLotto() {
        initRanks();
    }

    public void addMatchedResultAsRank(Map<Integer, Boolean> matchedLottos) {
        for (Map.Entry<Integer, Boolean> entry : matchedLottos.entrySet()) {
            int matchCount = entry.getKey();
            boolean isMatchedBonus = entry.getValue();
            Rank rank = Rank.findRank(matchCount, isMatchedBonus);

            addRank(rank);
        }
    }

    private void addRank(Rank rank) {
        ranks.merge(rank, 1, Integer::sum);
    }

    public long getFinalPrizeAmount() {
        return calculateTotalPrizeAmount();
    }

    private long calculateTotalPrizeAmount() {
        long result = 0;

        for (Map.Entry<Rank, Integer> entry : ranks.entrySet()) {
            Rank rank = entry.getKey();
            int matchedCount = entry.getValue();
            long prizeMoney = rank.getPrizeMoney();

            if (isMatchedRank(matchedCount)) {
                result += (prizeMoney * matchedCount);
            }
        }
        return result;
    }

    private boolean isMatchedRank(int matchedCount) {
        return matchedCount > 0;
    }

    public EnumMap<Rank, Integer> getWinningStatistics() {
        return ranks;
    }

    private void initRanks() {
        for (Rank rank : Rank.values()) {
            ranks.put(rank, 0);
        }
    }

}
