import { useState } from 'react'
import { playersData, handCardsData, initialLogs } from './data/mockGameData'

import PlayerPanel from './components/PlayerPanel'
import CardItem from './components/CardItem'
import BattleLog from './components/BattleLog'
import ActionBar from './components/ActionBar'

function App() {
  // 상태로 바꿔서 관리
  const [players, setPlayers] = useState(playersData)
  const [handCards, setHandCards] = useState(handCardsData)
  const [logs, setLogs] = useState(initialLogs)

  return (
    <div>
      <h1>Lost Paradise</h1>

      {/* 플레이어 목록 */}
      <div>
        {players.map(player => (
          <PlayerPanel key={player.id} player={player} />
        ))}
      </div>

      {/* 로그 */}
      <BattleLog logs={logs} />

      {/* 카드 */}
      <div>
        {handCards.map(card => (
          <CardItem key={card.id} card={card} />
        ))}
      </div>

      {/* 버튼 */}
      <ActionBar />
    </div>
  )
}

export default App