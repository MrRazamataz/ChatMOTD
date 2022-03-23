## Bukkit to Nukkit, some info
* This is like notes on what I need to remember for going from the Bukkit API to Nukkit.

# Docs links (VERY USEFUL):
[Nukkit - All classes](https://ci.opencollab.dev/job/NukkitX/job/Nukkit/job/master/javadoc/allclasses-noframe.html)  
[Nukkit - Overview](https://ci.opencollab.dev/job/NukkitX/job/Nukkit/job/master/javadoc/overview-summary.html)

### Imports:
```
package raza.$chatmotd;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;
```
-->
```
package raza.$chatmotd;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import cn.nukkit.*;
import cn.nukkit.command.*;
import cn.nukkit.event.*;
import cn.nukkit.plugin.*;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.*;
```
`public class PluginMain extends JavaPlugin implements Listener` --> `public class PluginMain extends PluginBase implements Listener`


## Other files:

* In `plugin.yml`, change `api-version` to `api`.
