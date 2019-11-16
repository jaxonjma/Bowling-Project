package com.jaxon.bowling.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.jaxon.bowling.exception.BusinessException;
import com.jaxon.bowling.model.dto.GameDTO;

public final class GameUtil {
	
	private static final int TOTAL_FRAMES =10;
	private static final int TOTAL_PINES  =10;
	private static final ToIntFunction<GameDTO> VALIDATE_FAULT = gbpa-> gbpa.getResult()!=-1?gbpa.getResult():0;
	
	private GameUtil() { }
	
	public static Map<String,List<GameDTO>> getGamesByPlayers(List<GameDTO> games){
		return games.stream()
		.collect(Collectors.groupingBy(GameDTO::getPlayer));
	}

	public static Map<String,List<GameDTO>> sortedMapByNamePlayer(Map<String,List<GameDTO>> unsortMap){
		return unsortMap.entrySet().stream()
					    .sorted(Map.Entry.comparingByKey())
					    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
					    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	
	public static Map<Integer,List<GameDTO>> getGamesByFrame(List<GameDTO> games) {
		return games.stream().collect(Collectors.groupingBy(GameDTO::getFrame));
	}
	
	public static void validateGames(List<GameDTO> games) {
		Map<String,List<GameDTO>> gamesByPlayers= GameUtil.sortedMapByNamePlayer(GameUtil.getGamesByPlayers(games));
		gamesByPlayers.forEach((k,v)->{
			Map<Integer,List<GameDTO>> gamesByFrame = GameUtil.getGamesByFrame(v);
			gamesByFrame.forEach((kf,vf)->{
				if(kf<=TOTAL_FRAMES) {
					validateMaxAllowed(k,kf,vf);
					if(vf==null || vf.isEmpty()) {
						throw new BusinessException(String.format("Incomplete rolls for the player %s",k));
					}else if (vf.size()>2 && (kf<TOTAL_FRAMES || (kf==10 && vf.size()>3))) {
							throw new BusinessException(String.format("More results than expected for %s's frame %s have been found",k,kf));
					}else if(10==kf && vf.size()<3) {
						throw new BusinessException(String.format("Less results than expected for %s's frame %s have been found",k,kf));
					}
				}else {
					throw new BusinessException(String.format("More than 10 frames have been found for the player: %s", k)); 
				}
			});
		});
	}
	
	private static void validateMaxAllowed(String player, Integer frame, List<GameDTO> g) {
		if((frame<TOTAL_FRAMES && (g.stream().mapToInt(VALIDATE_FAULT).sum()>20 || g.stream().anyMatch(i->i.getResult()>TOTAL_PINES))) ||
		  (frame==TOTAL_FRAMES && (g.stream().mapToInt(VALIDATE_FAULT).sum()>30 || g.stream().anyMatch(i->i.getResult()>TOTAL_PINES)))){
			throw new BusinessException(String.format("A score greater than the total number of pines has been found for the player %s", player));
		}
	}
}
