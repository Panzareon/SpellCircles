package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.parts.*;

public class ModSpell
{
    //Constants
    public static final SpellPart Caster = new SpellPartCaster();
    public static final SpellPart GetTarget = new SpellPartGetTarget();
    public static final SpellPart Constant = new SpellPartConstant();
    public static final SpellPart Overworld = new SpellPartOverworld();
    public static final SpellPart PocketDimension = new SpellPartPocketDimension();
    public static final SpellPart DirectionSouth = new SpellPartDirectionSouth();
    public static final SpellPart DirectionDown = new SpellPartDirectionDown();
    public static final SpellPart DirectionEast = new SpellPartDirectionEast();
    public static final SpellPart DirectionNorth = new SpellPartDirectionNorth();
    public static final SpellPart DirectionUp = new SpellPartDirectionUp();
    public static final SpellPart DirectionWest = new SpellPartDirectionWest();
    public static final SpellPart EntitiesInArea = new SpellPartEntitiesInArea();

    //Operations
    public static final SpellPart LookingAt = new SpellPartLookingAt();
    public static final SpellPart ExpandPositionList = new SpellPartExpandPositionList();
    public static final SpellPart MovePositions = new SpellPartMovePositions();
    public static final SpellPart SameBlocks = new SpellPartSameBlocks();

    //Actions
    public static final SpellPart Damage = new SpellPartDamage();
    public static final SpellPart Heal = new SpellPartHeal();
    public static final SpellPart Regeneration = new SpellPartRegeneration();
    public static final SpellPart Motion = new SpellPartMotion();
    public static final SpellPart DimensionTeleport = new SpellPartDimensionTeleport();
    public static final SpellPart StopMotion = new SpellPartStopMotion();
    public static final SpellPart StopMotionInDirection = new SpellPartStopMotionInDirection();
    public static final SpellPart BreakBlock = new SpellPartBreakBlock();
    public static final SpellPart PickupBlock = new SpellPartPickupBlock();
    public static final SpellPart PlaceBlock = new SpellPartPlaceBlock();
    public static final SpellPart SetDay = new SpellPartSetDay();
    public static final SpellPart SetNight = new SpellPartSetNight();
    public static final SpellPart CallRain = new SpellPartCallRain();
    public static final SpellPart CallThunderstorm = new SpellPartCallThunderstorm();
    public static final SpellPart LightningBolt = new SpellPartLightningBolt();
    public static final SpellPart ClearWeather = new SpellPartClearWeather();

    public static final SpellPart Overload = new SpellPartOverload();
    public static final SpellPart EnhanceBareFist = new SpellPartEnhanceBareFist();
    public static final SpellPart EnhanceFoot = new SpellPartEnhanceFoot();
    public static final SpellPart EnhanceTool = new SpellPartEnhanceTool();
    public static final SpellPart EnhanceWeapon = new SpellPartEnhanceWeapon();
    public static final SpellPart Slowness = new SpellPartSlowness();
    public static final SpellPart Freeze = new SpellPartFreeze();

    public static final SpellPart SpawnFireSpirit = new SpellPartSpawnFireSpirit();

    public static final SpellPart Charge = new SpellPartCharge();
    public static final SpellPart ThrowSpell = new SpellPartThrowSpell();
    public static final SpellPart XTimes = new SpellPartXTimes();

    public static void init()
    {
        //Constants
        SpellList.registerSpell(Caster);
        SpellList.registerSpell(GetTarget, Caster);
        SpellList.registerSpell(Constant);
        SpellList.registerSpell(Overworld);
        SpellList.registerSpell(PocketDimension, Overworld);
        SpellList.registerSpell(DirectionSouth);
        SpellList.registerSpell(DirectionDown);
        SpellList.registerSpell(DirectionEast);
        SpellList.registerSpell(DirectionNorth);
        SpellList.registerSpell(DirectionUp);
        SpellList.registerSpell(DirectionWest);
        SpellList.registerSpell(EntitiesInArea, GetTarget);

        //Operations
        SpellList.registerSpell(LookingAt, GetTarget);
        SpellList.registerSpell(ExpandPositionList, MovePositions);
        SpellList.registerSpell(MovePositions, GetTarget);
        SpellList.registerSpell(SameBlocks, ExpandPositionList);

        //Actions
        SpellList.registerSpell(Damage, Heal);
        SpellList.registerSpell(Heal);
        SpellList.registerSpell(Regeneration, Heal);
        SpellList.registerSpell(Motion);
        SpellList.registerSpell(DimensionTeleport, Motion);
        SpellList.registerSpell(StopMotion, Motion);
        SpellList.registerSpell(StopMotionInDirection, StopMotion);
        SpellList.registerSpell(BreakBlock);
        SpellList.registerSpell(PickupBlock, BreakBlock);
        SpellList.registerSpell(PlaceBlock, BreakBlock);
        SpellList.registerSpell(SetDay);
        SpellList.registerSpell(SetNight, SetDay);
        SpellList.registerSpell(CallRain, SetDay);
        SpellList.registerSpell(CallThunderstorm, CallRain);
        SpellList.registerSpell(LightningBolt, CallThunderstorm);
        SpellList.registerSpell(ClearWeather, SetDay);

        SpellList.registerSpell(Overload, EnhanceWeapon);
        SpellList.registerSpell(EnhanceBareFist, Damage);
        SpellList.registerSpell(EnhanceFoot, EnhanceBareFist);
        SpellList.registerSpell(EnhanceTool, EnhanceBareFist);
        SpellList.registerSpell(EnhanceWeapon, EnhanceTool);
        SpellList.registerSpell(Slowness, StopMotion);
        SpellList.registerSpell(Freeze, Slowness);

        SpellList.registerSpell(SpawnFireSpirit, LightningBolt);

        SpellList.registerSpell(Charge);
        SpellList.registerSpell(ThrowSpell, Motion);
        SpellList.registerSpell(XTimes, Charge);
    }
}
