# Painting Patterns

These files are meant to replace the original Java code for loading a painting layout. Creating several dozen enums is tedious and error prone (it's easy to accidentally delete a line).

Each pattern is represented as an array of JSON strings of equal length. The strings contain symbols

The new JSON recipe format, introduced in Minecraft 1.12, was the inspiration for this format.

## Symbols In Original JSON Files

While the specific symbols used in each pattern don't matter, a specific convention was used in creating the original JSON files 

| Symbol | Size | Symbol | Size | Symbol | Size |
|:------:|:----:|:------:|:----:|:------:|:----:|
| A      | 1x1  | O      | 3x6  | c      | 6x3  |
| B      | 1x2  | P      | 3x7  | d      | 6x4  |
| C      | 1x3  | Q      | 4x1  | e      | 6x9  |
| D      | 1x4  | R      | 4x2  | f      | 7x5  |
| E      | 1x10 | S      | 4x3  | g      | 8x1  |
| F      | 2x1  | T      | 4x4  | h      | 8x2  |
| G      | 2x2  | U      | 4x5  | i      | 8x3  |
| H      | 2x3  | V      | 4x6  | j      | 8x4  |
| I      | 2x4  | W      | 4x8  | k      | 8x6  |
| J      | 3x1  | X      | 5x1  | l      | 8x8  |
| K      | 3x2  | Y      | 5x5  | m      | 8x12 |
| L      | 3x3  | Z      | 5x7  | n      | 9x6  |
| M      | 3x4  | a      | 5x8  | o      | 10x1 |
| N      | 3x5  | b      | 5x10 | p      | 10x5 |

## Lookup Table

This lookup table is the reverse of the original symbol table, and can be used to to find the symbol for a certain painting size used in the original JSON files. If you wish to edit those files, or continue using the original convention, you might find this useful.

|     | 1   | 2   | 3   | 4   | 5   | 6   | 7   | 8   | 9   | 10  |
| :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
| 1   | A   | F   | J   | Q   | X   |     |     | g   |     | o   |
| 2   | B   | G   | K   | R   |     |     |     | h   |     |     |
| 3   | C   | H   | L   | S   |     | c   |     | i   |     |     |
| 4   | D   | I   | M   | T   |     | d   |     | j   |     |     |
| 5   |     |     | N   | U   | Y   |     | f   |     |     | p   |
| 6   |     |     | O   | V   |     |     |     | k   | n   |     |
| 7   |     |     | P   |     | Z   |     |     |     |     |     |
| 8   |     |     |     | W   | a   |     |     | l   |     |     |
| 9   |     |     |     |     |     | e   |     |     |     |     |
| 10  | E   |     |     |     | b   |     |     |     |     |     |
| 12  |     |     |     |     |     |     |     | m   |     |     |

## Sample Layout

```json
{
  "type": "subaraki:pattern",
  "name": "sphax",
  "pattern": [
    "AAAAAAAJJJCC    ",
    "FFFFAAAJJJCC    ",
    "FFFFFFFFFFCC    ",
    "DDDDCHHYYYYY    ",
    "BBBBCHHYYYYYSSSS",
    "BBBBCHHYYYYYSSSS",
    "RRRRKKKYYYYYSSSS",
    "RRRRKKKYYYYYSSSS",
    "GGGGGGGGGGGGSSSS",
    "GGGGGGGGGGGGSSSS",
    "GGGGGGGGGGGGSSSS",
    "GGGGGGGGGGGGSSSS",
    "TTTTTTTTTTTTSSSS",
    "TTTTTTTTTTTTSSSS",
    "TTTTTTTTTTTTSSSS",
    "TTTTTTTTTTTTSSSS"
  ],
  "key": {
    "A": {"x": 1, "y": 1},
    "B": {"x": 1, "y": 2},
    "C": {"x": 1, "y": 3},
    "D": {"x": 1, "y": 4},
    "F": {"x": 2, "y": 1},
    "G": {"x": 2, "y": 2},
    "H": {"x": 2, "y": 3},
    "J": {"x": 3, "y": 4},
    "K": {"x": 3, "y": 2},
    "R": {"x": 4, "y": 2},
    "S": {"x": 4, "y": 3},
    "T": {"x": 4, "y": 4},
    "Y": {"x": 5, "y": 5}
  }
}
```

##
