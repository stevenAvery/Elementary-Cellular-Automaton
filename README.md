# Elementary Cellular Automaton
## Introduction
Computes [elementary cellular automaton](http://mathworld.wolfram.com/ElementaryCellularAutomaton.html) from a given rule, and outputs the result to the console, or to a png image.

## Usage
Can run the program directly with leiningen using the command `lein run RULE [options]` from terminal.
Or it can be compiled to a standalone jar file using:
``` bash
lein uberjar
java -jar ElementaryCellularAutomaton-1.0.0-standalone.jar RULE [options]
```

Command-line line args are as follows
```
Usage: program RULE [options]

Options:
  -i, --iterations ITERATIONS  32              Number of iterations to compute
  -o, --imageName IMAGE_NAME   res/output.png  Name of the output image
  -p, --png                                    Boolean to output to png
  -t, --terminal                               Boolean to output to terminal
  -c, --cellSize CELL_SIZE     4               The size of the cells in the output image
  -h, --help

Arguments:
    RULE    Rule for elementary cellular automaton
            Must be a number between 0 and 255
```

## Tutorial
- For these examples the program will be run as `lein run`, but it can be run in any of the ways described in usage.

By default this program will output 32 iterations of the given rule to the terminal
```
$ lein run 150
                               #                               
                              ###                              
                             # # #                             
                            ## # ##                            
                           #   #   #                           
                          ### ### ###                          
                         # #   #   # #                         
                        ## ## ### ## ##                        
                       #       #       #                       
                      ###     ###     ###                      
                     # # #   # # #   # # #                     
                    ## # ## ## # ## ## # ##                    
                   #   #       #       #   #                   
                  ### ###     ###     ### ###                  
                 # #   # #   # # #   # #   # #                 
                ## ## ## ## ## # ## ## ## ## ##                
               #               #               #               
              ###             ###             ###              
             # # #           # # #           # # #             
            ## # ##         ## # ##         ## # ##            
           #   #   #       #   #   #       #   #   #           
          ### ### ###     ### ### ###     ### ### ###          
         # #   #   # #   # #   #   # #   # #   #   # #         
        ## ## ### ## ## ## ## ### ## ## ## ## ### ## ##        
       #       #               #               #       #       
      ###     ###             ###             ###     ###      
     # # #   # # #           # # #           # # #   # # #     
    ## # ## ## # ##         ## # ##         ## # ## ## # ##    
   #   #       #   #       #   #   #       #   #       #   #   
  ### ###     ### ###     ### ### ###     ### ###     ### ###  
 # #   # #   # #   # #   # #   #   # #   # #   # #   # #   # #
## ## ## ## ## ## ## ## ## ## ### ## ## ## ## ## ## ## ## ## ##
```

The number of iterations can be changed using the `-i` option
```
$ lein run 150 -i 16

               #               
              ###              
             # # #             
            ## # ##            
           #   #   #           
          ### ### ###          
         # #   #   # #         
        ## ## ### ## ##        
       #       #       #       
      ###     ###     ###      
     # # #   # # #   # # #     
    ## # ## ## # ## ## # ##    
   #   #       #       #   #   
  ### ###     ###     ### ###  
 # #   # #   # # #   # #   # #
## ## ## ## ## # ## ## ## ## ##
```

Alternatively, the output can be directed a png rather than the console, using `-p`.
```
$ lein run 150 -p
```
![Output for lein run 150 -p](/res/ECA_150.png)

The output location, and cell size of the image can be changed using `-o` and `-c` respectively.
```
$ lein run 150 -p -o res/ECA_150_small.png -c 2
```
![Output for lein run 150 -p -o res/ECA_150_small.png -c 2](/res/ECA_150_small.png)

For larger computations, `-v` can be used to view progress.
```
$ lein run 150 -p -i 256 -c 2 -v
100% [========================================] cellular automaton              
 33% [==============                          ] image cells [line 85 of 255]    
```
![Output for lein run 150 -p -i 256 -c 2 -v](/res/ECA_150_large.png)


## Further examples

## Future Improvements
- option to input initial line of cells
- option for fixed width of cell lines
- multi-threading?
