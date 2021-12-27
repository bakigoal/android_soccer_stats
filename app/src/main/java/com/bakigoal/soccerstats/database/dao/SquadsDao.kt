package com.bakigoal.soccerstats.database.dao

import androidx.room.*
import com.bakigoal.soccerstats.database.entity.SquadDB
import com.bakigoal.soccerstats.database.entity.SquadPlayerEntity
import com.bakigoal.soccerstats.database.entity.SquadTeamEntity

@Dao
abstract class SquadsDao {

    @Transaction
    @Query("select * from squad_team where id=:teamId limit 1")
    abstract fun getTeamSquad(teamId: Int): SquadDB?

    @Transaction
    @Query("select id from squad_team")
    abstract fun getIds():List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(squadPlayerEntity: SquadPlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(squadTeamEntity: SquadTeamEntity)

    @Transaction
    open fun insertAll(vararg squads: SquadDB) {
        squads.forEach { squad ->
            insert(squad.team)
            squad.players.forEach(this::insert)
        }
    }
}