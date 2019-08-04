# Paintings++

README.md by [Joel Murphy](#credits)

_Paintings++_, now with

 _Painting Selection GUI Revamped_ built in, 

## Just the GUI

If you only want to use the selection GUI, there is no need to configure anything. Just install the mod through Twitch, or by [manual installation][link_install_mod], and you're all set.

## Art Collections

_Paintings++_ uses a **texture**, which is a single PNG image, divided up into cells, with each **cell** representing one picture; and a **pattern**, describing how the image is divided. We call this pairing, of texture and pattern, an **art collection**.

The _**vanilla**_ art collection is selected by default on a new install of _Paintings++_. To change to a different collection, you will first need the configuration file, `morepaintings.cfg` to be created. Running Minecraft after you've installed the _Paintings++_ `.jar` file, the configuration file will be automatically created in the `config` subfolder of your Forge installation. If you need help finding the Forge folder, see the _manual installation_ link under [Just the GUI](#just-the-gui).

Open the configuration file, `morepaintings.cfg`, and enter the name of the collection under `painting mode`. Valid names for built-in patterns are _**gibea**_, _**sphax**_, _**tinypics**_, _**mediumpics**_, _**insane**_, _**new_insane**_, _**extended**_, and _**massive**_. You can also set the name to _**vanilla**_ or to the name of a [custom art collection](#custom-art-collections). After editing the config, if Minecraft is still running, you'll need to close Minecraft and restart.

If you don't want to add a texture file, the _**gibea**_ collection comes preloaded with a complete set of paintings. Otherwise, you'll need a `.png` file containing all of your pictures. You can find a few contributed 

### Built-in Patterns

Eight collections of varying size, and quantity of pictures, are built into _Paintings++_. Some of the collections come with default images in each cell and, wherever there isn't a default image, there's a solid color for each painting, based on its size and shape.

Below is a table summarizing the built-in collection. Clicking on the collection name will expand the row to show a thumbnail of the texture template. Click either the template, or the download symbol (<span class="fa fa-download"> </span>), to download a texture.

| Collection                                                                                            | Resolution<sup>1</sup> | # Cells | # Pictures | Pattern Size<sup>2</sup> |                                                          |
| :---------------------------------------------------------------------------------------------------- | :--------------------: | :-----: | :--------: | :----------------------: | :------------------------------------------------------: |
| <details><summary>gibea</summary>[![gibea.png][link_gibea]][link_gibea]</details>                     | 256&times;256          | 88      | 88         | 16&times;16              | [<span class="fa fa-download"> </span>][link_gibea]      |
| <details><summary>sphax</summary>[![sphax.png][link_sphax]][link_sphax]</details>                     | 256&times;256          | 50      | 26         | 16&times;16              | [<span class="fa fa-download"> </span>][link_sphax]      |
| <details><summary>tinypics</summary>[![tinypics.png][link_tinypics]][link_tinypics]</details>         | 512&times;512          | 302     | —          | 32&times;32              | [<span class="fa fa-download"> </span>][link_tinypics]   |
| <details><summary>mediumpics</summary>[![mediumpics.png][link_mediumpics]][link_mediumpics]</details> | 512&times;512          | 222     | —          | 32&times;32              | [<span class="fa fa-download"> </span>][link_mediumpics] |
| <details><summary>new_insane</summary>[![new_insane.png][link_new_insane]][link_new_insane]</details> | 512&times;512          | 121     | 42         | 32&times;32              | [<span class="fa fa-download"> </span>][link_new_insane] |
| <details><summary>insane</summary>[![insane.png][link_insane]][link_insane]</details>                 | 512&times;512          | 121     | 26         | 32&times;32              | [<span class="fa fa-download"> </span>][link_insane]     |
| <details><summary>extended</summary>[![extended.png][link_extended]][link_extended]</details>         | 512&times;512          | 171     | 26         | 32&times;32              | [<span class="fa fa-download"> </span>][link_extended]   |
| <details><summary>massive</summary>[![massive.png][link_massive]][link_massive]</details>             | 1008&times;1008        | 281     | —          | 63&times;63              | [<span class="fa fa-download"> </span>][link_massive]    |

<small>

1. These reference textures use 16&times;16 pixels per block but higher detail paintings can be made by creating a larger texture. Integer multiples of 16&times;16 are recommended (e.g. 32&times;32, 64&times;64, and so on) for each block.
2. Pattern size is in blocks.

</small>

_**Q: Do you mean I have to buy something like Adobe Photoshop?**_  
_**A:**_ Not at all. There are numerous free image editors such as [Krita](https://krita.org/), [GIMP](https://www.gimp.org/), and [Paint.net](https://www.getpaint.net/). If you'd like to draw some of your own pixel art, [Pixilart](https://www.pixilart.com/) has a pretty neat editor that works right inside your web browser.

### Custom Patterns

## Useless Tables

### Sizes in Default Templates

This table shows the distribution of painting sizes in each collection aswell as the symbol used for each size. There is no requirement that you use the symbols we did, only that each symbol have only one size.

| Symbol | Size  | Area | Gibea | Sphax | Tinypics | Mediumpics | Insane<sup>1</sup> | Extended | Massive |
| :----: | :---: | :--: | :---: | :---: | :------: | :--------: | :----------------: | :------: | :-----: |
| A      | 1x1   | 1    | 32    | 18    | 76       | 48         | 11                 | 32       | 20      |
| B      | 1x2   | 2    | 12    | 4     | 32       | 10         | 11                 | 12       | 16      |
| C      | 1x3   | 3    |       | 3     | 32       | 5          | 6                  | 8        | 5       |
| D      | 1x4   | 4    |       |       | 4        |            |                    |          | 3       |
| E      | 1x10  | 10   |       |       |          |            |                    |          | 1       |
| F      | 2x1   | 2    | 38    | 12    | 32       | 24         | 14                 | 28       | 20      |
| G      | 2x2   | 4    | 11    | 12    | 36       | 64         | 18                 | 12       | 1       |
| H      | 2x3   | 6    |       | 1     | 16       | 16         | 6                  | 16       | 11      |
| I      | 2x4   | 8    |       |       | 4        | 4          |                    | 5        | 8       |
| J      | 3x1   | 3    |       | 2     | 32       |            | 6                  | 8        | 5       |
| K      | 3x2   | 6    |       | 1     | 16       | 18         | 2                  | 16       | 19      |
| L      | 3x3   | 9    |       |       | 8        | 9          | 3                  | 8        | 7       |
| M      | 3x4   | 12   |       |       |          |            |                    | 6        | 7       |
| N      | 3x5   | 15   |       |       |          |            |                    |          | 5       |
| O      | 3x6   | 18   |       |       |          |            |                    |          | 4       |
| P      | 3x7   | 21   |       |       |          |            |                    |          | 2       |
| Q      | 4x1   | 4    |       | 1     | 8        |            | 8                  | 4        | 12      |
| R      | 4x2   | 8    | 1     | 1     | 4        | 10         | 5                  | 5        | 18      |
| S      | 4x3   | 12   | 2     | 4     | 5        | 16         | 8                  | 12       | 20      |
| T      | 4x4   | 16   | 3     | 3     | 3        | 8          | 10                 | 3        | 6       |
| U      | 4x5   | 20   |       |       |          |            |                    |          | 1       |
| V      | 4x6   | 24   |       |       |          |            | 4                  | 4        | 16      |
| W      | 4x8   | 32   |       |       |          |            | 2                  |          | 4       |
| X      | 5x1   | 5    |       |       |          |            |                    |          | 5       |
| Y      | 5x5   | 25   |       | 1     |          |            | 2                  |          | 7       |
| Z      | 5x7   | 35   |       |       |          |            |                    |          | 5       |
| a      | 5x8   | 40   |       |       |          |            |                    |          | 5       |
| b      | 5x10  | 50   |       |       |          |            |                    |          | 2       |
| c      | 6x3   | 18   |       |       |          |            |                    |          | 6       |
| d      | 6x4   | 24   |       |       |          |            |                    | 4        | 10      |
| e      | 6x9   | 54   |       |       |          |            |                    |          | 3       |
| f      | 7x5   | 35   |       |       |          |            |                    |          | 4       |
| g      | 8x1   | 8    |       |       |          |            | 1                  |          |         |
| h      | 8x2   | 16   |       |       |          |            |                    |          | 5       |
| i      | 8x3   | 24   |       |       |          |            |                    |          | 1       |
| j      | 8x4   | 32   |       |       |          |            | 4                  |          | 8       |
| k      | 8x6   | 48   |       |       |          |            | 1                  |          |         |
| l      | 8x8   | 64   |       |       |          |            | 1                  |          | 1       |
| m      | 8x12  | 96   |       |       |          |            |                    |          | 1       |
| n      | 9x6   | 54   |       |       |          |            |                    |          | 4       |
| o      | 10x1  | 10   |       |       |          |            |                    |          | 2       |
| p      | 10x5  | 50   |       |       |          |            |                    |          | 2       |

<small>

1. Includes both Insane and New Insane.

</small>

## Credits

### Current Maintainers

<style type="text/css">@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.css);</style>
* Subaraki, a.k.a. ArtixAllMighty
* Joel Murphy, a.k.a. Murphy's Chaos [<span class="fa fa-twitter"> </span>](http://www.twitter.com/murphyschaos) [<span class="fa fa-github"> </span>](http://www.github.com/MurphysChaos)

## Final Word

Check out [this page](https://imgur.com/gallery/R7qao). It's an article about the origins of the original Minecraft paintings.

[link_install_mod]: https://www.minecraftmods.com/how-to-install-mods-for-minecraft-forge/
[link_gibea]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/gibea.png?raw=true
[link_sphax]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/sphax.png?raw=true
[link_tinypics]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/tinypics.png?raw=true
[link_mediumpics]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/mediumpics.png?raw=true
[link_new_insane]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/new_insane.png?raw=true
[link_insane]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/insane.png?raw=true
[link_extended]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/extended.png?raw=true
[link_massive]: https://github.com/ArtixAllMighty/Paintings--/blob/master/src/main/resources/assets/subaraki/art/massive.png?raw=true

<style type="text/css">@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.css);img{max-width:192px;}</style>
