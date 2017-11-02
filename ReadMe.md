# Paintings++

> _**Q:** Why is this repository called Paintings-- but the mod is called Paintings++?_<br>
> _**A:** Because you can't have a file with a plus in its name._

Paintings++ lets you to replace the paintings in Minecraft. By loading a compatible resource file, and changing a configuration setting, new paintings can be added to your world.

Paintings++ has goes great with [Painting Selection Gui Revamped](https://mods.curse.com/mc-mods/minecraft/252043-painting-selection-gui-revamped), a mod that lets you pick which painting you're hanging.

## Resource File

Resource files are most commonly known for their use in changing block textures. While all block textures go into a resource file, not all resource files are contain block textures.

Paintings++ uses resource files to store the image file used to texture paintings.

```
<root resource folder>
  ├─ assets/
  │   └─ subaraki/
  │       └─ art/
  │           └─ <pattern>.png
  ├─ pack.mcmeta
  └─ pack.png
```

| Pattern                     | Size (in blocks) | # of Pictures | Picture Sizes (in blocks)                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| :-------------------------- | :--------------: | :-----------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Gibea<br>(gibea)            | 16&times;16      | 88            | 1&times;1, 1&times;2, 2&times;1, 2&times;2, 4&times;2, 4&times;3, 4&times;4                                                                                                                                                                                                                                                                                                                                                                                 |
| Sphax<br>(sphax)            | 16&times;16      | 50            | 1&times;1, 1&times;2, 1&times;3, 1&times;4, 2&times;1, 2&times;2, 2&times;3, 3&times;1, 3&times;2, 4&times;2, 4&times;3, 4&times;4, 5&times;5                                                                                                                                                                                                                                                                                                               |
| Tiny<br>(tinypics)          | 32&times;32      | 302           | 1&times;1, 1&times;2, 1&times;3, 1&times;4, 2&times;1, 2&times;2, 2&times;3, 2&times;4, 3&times;1, 3&times;2, 3&times;3, 4&times;1, 4&times;2, 4&times;3, 4&times;4                                                                                                                                                                                                                                                                                         |
| Medium<br>(mediumpics)      | 32&times;32      | 222           | 1&times;1, 1&times;2, 1&times;3, 2&times;1, 2&times;2, 2&times;3, 2&times;4, 3&times;2, 3&times;3, 4&times;2, 4&times;3, 4&times;4                                                                                                                                                                                                                                                                                                                          |
| New Insane*<br>(new_insane) | 32&times;32      | 121           | 1&times;1, 1&times;2, 1&times;3, 2&times;1, 2&times;2, 2&times;3, 3&times;1, 3&times;2, 3&times;3, 4&times;1, 4&times;2, 4&times;3, 4&times;4, 4&times;6, 4&times;8, 5&times;5, 8&times;1, 8&times;4, 8&times;6, 8&times;8                                                                                                                                                                                                                                  |
| Insane*<br>(insane)         | 32&times;32      | 121           | 1&times;1, 1&times;2, 1&times;3, 2&times;1, 2&times;2, 2&times;3, 3&times;1, 3&times;2, 3&times;3, 4&times;1, 4&times;2, 4&times;3, 4&times;4, 4&times;6, 4&times;8, 5&times;5, 8&times;1, 8&times;4, 8&times;6, 8&times;8                                                                                                                                                                                                                                  |
| Extended<br>(extended)      | 32&times;32      | 171           | 1&times;1, 1&times;2, 1&times;3, 2&times;1, 2&times;2, 2&times;3, 2&times;4, 3&times;1, 3&times;2, 3&times;3, 3&times;4, 4&times;1, 4&times;2, 4&times;3, 4&times;4, 4&times;6, 6&times;4                                                                                                                             |
| Massive<br>(massive)        | 63&times;63      | 281           | 1&times;1, 1&times;2, 1&times;3, 1&times;4, 1&times;10, 2&times;1, 2&times;2, 2&times;3, 2&times;4, 3&times;1, 3&times;2, 3&times;3, 3&times;4, 3&times;5, 3&times;6, 3&times;7, 4&times;1, 4&times;2, 4&times;3, 4&times;4, 4&times;5, 4&times;6, 4&times;8, 5&times;1, 5&times;5, 5&times;7, 5&times;8, 5&times;10, 6&times;3, 6&times;4, 6&times;9, 7&times;5, 8&times;2, 8&times;3, 8&times;4, 8&times;8, 8&times;12, 9&times;6, 10&times;1, 10&times;5 |

\* The _new\_insane_ pattern is recommended over _insane_. The original _insane_ pattern was made to mimic _sphax_ and, as a result, the pictures are randomly laid out. Pictures in the _new\_insane_ pattern are grouped together by size.

While the specific symbols used in each pattern don't matter, a specific convention was used in creating the original JSON files

# Reference Tables

## Symbols in Default Templates

| Symbol | Size  | Symbol | Size  | Symbol | Size  |
| :----: | :---: | :----: | :---: | :----: | :---: |
| A      | 1x1   | O      | 3x6   | c      | 6x3   |
| B      | 1x2   | P      | 3x7   | d      | 6x4   |
| C      | 1x3   | Q      | 4x1   | e      | 6x9   |
| D      | 1x4   | R      | 4x2   | f      | 7x5   |
| E      | 1x10  | S      | 4x3   | g      | 8x1   |
| F      | 2x1   | T      | 4x4   | h      | 8x2   |
| G      | 2x2   | U      | 4x5   | i      | 8x3   |
| H      | 2x3   | V      | 4x6   | j      | 8x4   |
| I      | 2x4   | W      | 4x8   | k      | 8x6   |
| J      | 3x1   | X      | 5x1   | l      | 8x8   |
| K      | 3x2   | Y      | 5x5   | m      | 8x12  |
| L      | 3x3   | Z      | 5x7   | n      | 9x6   |
| M      | 3x4   | a      | 5x8   | o      | 10x1  |
| N      | 3x5   | b      | 5x10  | p      | 10x5  |

## Sizes in Default Templates

This lookup table is the reverse of the original symbol table, and can be used to to find the symbol for a certain painting size used in the original JSON files. If you wish to edit those files, or continue using the original convention, you might find this useful.

|       | 1     | 2     | 3     | 4     | 5     | 6     | 7     | 8     | 9     | 10    |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 1     | A     | F     | J     | Q     | X     |       |       | g     |       | o     |
| 2     | B     | G     | K     | R     |       |       |       | h     |       |       |
| 3     | C     | H     | L     | S     |       | c     |       | i     |       |       |
| 4     | D     | I     | M     | T     |       | d     |       | j     |       |       |
| 5     |       |       | N     | U     | Y     |       | f     |       |       | p     |
| 6     |       |       | O     | V     |       |       |       | k     | n     |       |
| 7     |       |       | P     |       | Z     |       |       |       |       |       |
| 8     |       |       |       | W     | a     |       |       | l     |       |       |
| 9     |       |       |       |       |       | e     |       |       |       |       |
| 10    | E     |       |       |       | b     |       |       |       |       |       |
| 12    |       |       |       |       |       |       |       | m     |       |       |

## Texture Templates
These templates give you paintings at a resolution of 16&times;16 pixels per block, which is the resolution of the vanilla textures. Several of the templates come with pictures already added to get you started, but those pictures can be replaced.

To make higher resolution paintings, you'll need to resize the template you're working with.

The Gibea texture is the original picture set included as part of this mod. It contains both the vanilla paintings and a lot of new ones.

| Pattern    | Template                                                                                                                                  |
| :--------- | :---------------------------------------------------------------------------------------------------------------------------------------- |
| Gibea      | ![gibea.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/gibea.png?raw=true)           |
| Sphax      | ![sphax.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/sphax.png?raw=true)           |
| Tiny       | ![tinypics.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/tinypics.png?raw=true)     |
| Medium     | ![mediumpics.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/mediumpics.png?raw=true) |
| New Insane | ![new_insane.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/new_insane.png?raw=true) |
| Insane     | ![insane.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/insane.png?raw=true)         |
| Extended    | ![extended.png](https://github.com/Kisutora/Paintings--/blob/master/src/main/resources/assets/subaraki/art/extended.png?raw=true)     |
| Massive    | ![massive.png](https://github.com/MurphysChaos/Paintings--/blob/master/src/main/resources/assets/subaraki/art/massive.png?raw=true)       |
