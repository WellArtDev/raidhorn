# RaidHorn

Never get ambushed by a surprise raid again. Bad Omen is the effect
that triggers a raid when you walk into a village — and vanilla gives
you no obvious heads-up that you're carrying it. RaidHorn watches your
effect list, and the moment Bad Omen lands it blasts a war-horn sound
and flashes a big red "RAID IMMINENT — Bad Omen!" warning above the
hotbar for about five seconds.

## Features

- **Bad Omen detection** — watches the player every 5 ticks for the
  Bad Omen effect
- **War-horn blast** — plays the raid horn sound the instant the
  effect is applied
- **HUD warning** — big red "RAID IMMINENT — Bad Omen!" text above
  the hotbar, visible for ~5 seconds
- **One-shot per application** — the warning re-arms only when the
  effect wears off, never nags
- **Zero config** — drop it in and play

## Install

1. Install [Fabric Loader](https://fabricmc.net/use/) 0.19.2+ for
   Minecraft 26.2
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) for
   Minecraft 26.2
3. Drop `raidhorn-1.0.0.jar` into your `mods` folder
4. Launch and play — no config needed

## Notes

- Client-side only. Works on any server (including vanilla).
- No mixins, no networking, nothing saved to disk.

## License

MIT — see [LICENSE](LICENSE).
