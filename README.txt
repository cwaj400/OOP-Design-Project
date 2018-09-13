CS3500 Object-Oriented Design - Summer I 2018
Will Angell-James & Kabir Dugal
Assignment 7 :

Will used the visitor pattern to apply animations to shapes, and Kabir used something similar,
Our program is a combination of both models. We combined them for a multitude of reasons, not least considering:
 1) Will used hash maps, mapping shape name to IShape. Kabir used an arraylist IShape in the model. Although
 both would have worked fine, we are more comfortable with working with lists than maps. The sorting also becomes easier.
 2) Our animation operations were largely similar. Will initially intended to use enums to specify which operation
 we are working with but eventually realised they were unnecessary and were data-hogs, as most implementations could be done without it.

We have a list of shapes and operations in our model - the shapes and operations. We used the
adaptor pattern to make multiple types of model interfaces. The IWrite model interface was created such that
it contained the public methods that could edit the data in the model. The IRead model interface contained methods that could access
this data, containing getters and a method that parsed through data to help output a formatted string.
The base class for both the IReadModel and IWriteModel interfaces is the IAnimationModel interface.
Our tick rate (number of ticks/second) is contained within the model and is set to 25 ticks/second automatically. This is in
seconds so we made the SVG view into milliseconds.

The tweenmodelbuilder is in the IAnimationModelImpl class which implements the IAnimationModel.

We abstracted a lot of commands and ideas into an abstract class. The same can be said for Operations.
We have interfaces and abstract classes for everything. Our operations are objects which get created with destinations
such as toColour and toDestination. The shapes are stored in the abstract class, so we needn't specify froms.

We have a printSVG and a printDescription methods for the shapes.

The tweening of our animation is all done in the shape classes. Our Color is set to float which is determined in the visual view.
The Posn is set to double.
We have a SortByOperationStartTime and a shape sorting class also. These are used to print shapes in the correct time order in text view.

Out EasyAnimator is the entrance of our program and a lot of the logic is done there. The args are parsed and
set to the correct values.

Our RunAnimation is our testing main class which we used to test our methods through the main.

The text view and SVG view are technically both text views (just formatted text), so they both implement the ITextView.
The visual view implements the IVisualView. These interfaces both extend the IAnimationView interface.
Our makeView(IModel) creates the view depending on the view type using dynamic dispatch.

**************
Assignment 07 Changes

Created HybridView, which was an implementation of the interactive AnimationView.
This view allows the user to press different buttons to achieve different functionalities such as  pausing/playing an animation, restarting it, speeding up the animation, slowing down the animation and exporting it as an SVG file to the desired file path.

Also, a controller was added, which separated the action listeners of the buttons in the view to the controller, keeping MVC in check. This also allowed us to create a method go() which runs the animation.

This is an overview of the structure of this program:

                                        MODEL
interface                            IAnimationModel

interface     IReadModel                           IWriteModel
              + String printAnimationState()       + void createShape(IShape shape)
              + ArrayList<IShape> getShapes()      + void removeShape(IShape shape)
              + int    getTickRate()               + void createOperation(IOperation operation)
                                                   + void setSpeed(int speed)

class                              IAnimationModelImpl


interface                            OPERATIONS
                                      IOperation
                                      + void   command(IShape shape)
                                      + String getDescription(IReadModel model)
                                      + int    getFromTime()
                                      + int    getToTime()
                                      + String printSVG()
                                      + String getName()


abstract class                        AOperation

class                                 Move
class                                 Scale
class                                 ChangeColor

                                      SHAPES
interface                             IShape
                                      + String getName()
                                      + String getType()
                                      + Posn   getPosn()
                                      + Color  getColor()
                                      + int    getAppearTime()
                                      + int    getDisappearTime()
                                      + ArrayList<IOperation> getOperations()
                                      + String getDescription()
                                      + double getWidth()
                                      + double getHeight()
                                      + String printSVG()
                                      + IShape getStateAt(int t)

abstract class                        AShape
class                                 Oval
class                                 Rectangle

                                      UTIL
class  Color                          Posn
       + float getRedval()            + double getX()
       + float getGreenval()          + double getY()
       + float getBlueval()           + void setX(double x)
       + void setRedval(float val)    + void setY(double y)
       + void setGreenval(float val)
       + void setBlueval(float val)
       + String getRGB()

class SortByAppearTime, SortByOperationStartTime, AnimationFileReader
interface TweenModelBuilder

                                  CONTROLLER
interface                        IAnimationController
                                 + String getInputFile()
                                 + void go()

class                            IAnimationControllerImpl

                                 VIEW
interface                        IAnimationView

class                            TextualViewImpl
class                            SVGViewImpl
class                            HybridView
class                            VisualView

class AnimationPanel, ExportSVGHandler, PlayButtonHandler, RestartButtonHandler, SlowDownAnimationHandler, SpeedUpAnimationHandler

enum TypeOfView





